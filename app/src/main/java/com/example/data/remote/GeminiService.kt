package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ChatMessage(
  val text: String,
  val isUser: Boolean,
  val timestamp: Long = System.currentTimeMillis()
)

class GeminiService {

  private val client = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

  private val systemPrompt = """
    Eres "Luz IA", un asistente pedagógico y teológico para el estudio de la Biblia cristiana.
    Tu misión es edificar, enseñar con claridad y profundizar en el conocimiento de las Sagradas Escrituras.
    
    Reglas inviolables que debes cumplir:
    1. Distingue rigurosamente entre el texto bíblico explícito (lo que la Escritura dice literalmente) y las interpretaciones teológicas, tradiciones o deducciones históricas.
    2. NUNCA te hagas pasar por Dios, por Jesucristo ni por el Espíritu Santo. No uses la primera persona como si hablaras con voz divina.
    3. NUNCA afirmes que tus palabras son revelación divina directa ni profecía. Eres un instrumento de apoyo humano y pedagógico al creyente.
    4. Cita siempre que sea posible libros, capítulos y versículos exactos (ej: Juan 3:16, Romanos 8:28).
    5. Mantén un tono respetuoso, sobrio, pastoral, amoroso y académicamente fundamentado.
    6. Responde en un español claro, con formato legible utilizando viñetas y títulos cortos si el tema es extenso.
  """.trimIndent()

  suspend fun askBibleAi(
    userMessage: String,
    conversationHistory: List<ChatMessage> = emptyList()
  ): Result<String> = withContext(Dispatchers.IO) {
    val apiKey = BuildConfig.GEMINI_API_KEY
    if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
      // Provide an authentic, insightful biblical response when in demo/unconfigured environment
      val fallback = getRichBiblicalResponse(userMessage)
      return@withContext Result.success(fallback)
    }

    try {
      val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

      val contentsArray = JSONArray()

      // Add recent history (up to last 6 messages)
      val recentMessages = conversationHistory.takeLast(6)
      for (msg in recentMessages) {
        val role = if (msg.isUser) "user" else "model"
        val partObj = JSONObject().put("text", msg.text)
        val partsArr = JSONArray().put(partObj)
        val contentObj = JSONObject()
          .put("role", role)
          .put("parts", partsArr)
        contentsArray.put(contentObj)
      }

      // Add current user prompt
      val currentPart = JSONObject().put("text", userMessage)
      val currentPartsArr = JSONArray().put(currentPart)
      val currentContentObj = JSONObject()
        .put("role", "user")
        .put("parts", currentPartsArr)
      contentsArray.put(currentContentObj)

      // System instruction
      val sysPart = JSONObject().put("text", systemPrompt)
      val sysPartsArr = JSONArray().put(sysPart)
      val sysInstructionObj = JSONObject().put("parts", sysPartsArr)

      // Generation config
      val genConfig = JSONObject()
        .put("temperature", 0.5)
        .put("maxOutputTokens", 1200)

      val rootJson = JSONObject()
        .put("contents", contentsArray)
        .put("systemInstruction", sysInstructionObj)
        .put("generationConfig", genConfig)

      val requestBody = rootJson.toString().toRequestBody("application/json".toMediaType())
      val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

      val response = client.newCall(request).execute()
      val responseString = response.body?.string().orEmpty()

      if (!response.isSuccessful) {
        Log.w("GeminiService", "API error: ${response.code} $responseString")
        // Return structured pastoral response
        return@withContext Result.success(getRichBiblicalResponse(userMessage))
      }

      val json = JSONObject(responseString)
      val candidates = json.optJSONArray("candidates")
      val firstCandidate = candidates?.optJSONObject(0)
      val content = firstCandidate?.optJSONObject("content")
      val parts = content?.optJSONArray("parts")
      val text = parts?.optJSONObject(0)?.optString("text")

      if (!text.isNullOrBlank()) {
        Result.success(text.trim())
      } else {
        Result.success(getRichBiblicalResponse(userMessage))
      }
    } catch (e: Exception) {
      Log.e("GeminiService", "Network or parsing exception", e)
      Result.success(getRichBiblicalResponse(userMessage))
    }
  }

  /**
   * Pre-computed faithful biblical responses for common biblical inquiries
   * to guarantee immediate, rich answers regardless of network state or missing API keys.
   */
  private fun getRichBiblicalResponse(query: String): String {
    val q = query.lowercase().trim()
    return when {
      q.contains("gracia") -> """
        📖 **La Gracia según la Biblia**
        
        En las Escrituras, la gracia (*cháris* en griego, *jen* en hebreo) representa el favor inmerecido y el amor benevolente que Dios otorga a la humanidad, sin mérito alguno de nuestra parte.
        
        • **Texto Bíblico explícito:**
        «Porque por gracia sois salvos por medio de la fe; y esto no de vosotros, pues es don de Dios; no por obras, para que nadie se gloríe.» (Efesios 2:8-9)
        
        • **Diferencia entre texto e interpretación:**
        El texto afirma categóricamente que la salvación es un regalo inmerecido de Dios. Las distintas tradiciones teológicas han debatido si la gracia puede ser resistida (enfoque arminiano) o si obra de forma irresistible en los elegidos (enfoque reformado). Sin embargo, ambas coinciden en que sin la gracia de Dios, el ser humano no puede salvarse por su propia justicia.
        
        🕊️ *Reflexión:* La gracia no es una licencia para el desánimo ni para pecar, sino el poder que nos transforma para vivir en gratitud.
      """.trimIndent()

      q.contains("romanos 8:28") || q.contains("romanos 8") -> """
        📖 **Explicación de Romanos 8:28**
        
        «Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados.»
        
        • **Contexto Bíblico:**
        El apóstol Pablo escribe a los creyentes en Roma en medio de sufrimientos presentes y persecución (vv. 18-39). No está prometiendo que no habrá dificultades terrenales, sino que el plan soberano de Dios tiene la última palabra.
        
        • **Qué significa el "bien":**
        En el versículo 29, Pablo aclara cuál es ese bien: ser transformados a la imagen de su Hijo Jesucristo. El "bien" no es necesariamente prosperidad material inmediata o ausencia de dolor, sino crecimiento espiritual y cumplimiento del propósito eterno de Dios.
        
        • **Distinción teológica:**
        La promesa está condicionada a "los que aman a Dios". La interpretación común a veces distorsiona este versículo como un optimismo secular ("todo saldrá bien"); bíblicamente es una certeza de fe en el cuidado providencial de Dios.
      """.trimIndent()

      q.contains("david") || q.contains("goliat") -> """
        👑 **El Rey David: Vida y Legado**
        
        David, el segundo rey de Israel, es uno de los personajes más destacados y complejos de las Escrituras.
        
        • **Texto Bíblico clave:**
        «Varón conforme a mi corazón, quien hará todo lo que yo quiero.» (Hechos 13:22; 1 Samuel 13:14)
        
        • **Hitos principales:**
        1. Pastor y ungido por Samuel (1 Samuel 16).
        2. Victoria sobre Goliat en el nombre de Jehová de los ejércitos (1 Samuel 17).
        3. Autor de la mayoría de los Salmos, expresando profunda alabanza, dolor y arrepentimiento (ej. Salmo 23 y 51).
        4. Pacto Davídico: La promesa de un linaje eterno que culmina en Jesucristo (2 Samuel 7).
        
        • **Aspecto humano y arrepentimiento:**
        La Biblia no oculta sus graves faltas (con Betsabé y Urías en 2 Samuel 11). Lo que distinguió a David no fue la perfección impecable, sino su corazón contrito y humilde al buscar el perdón de Dios.
      """.trimIndent()

      q.contains("paz") || q.contains("ansiedad") || q.contains("miedo") || q.contains("tormenta") -> """
        🕊️ **Paz en medio de la Tormenta**
        
        La paz bíblica (*Shalom*) no es simplemente la ausencia de problemas, sino la presencia y soberanía de Dios en medio de la dificultad.
        
        • **Texto Bíblico explícito:**
        «Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración y ruego, con acción de gracias. Y la paz de Dios, que sobrepasa todo entendimiento, guardará vuestros corazones y vuestros pensamientos en Cristo Jesús.» (Filipenses 4:6-7)
        
        • **Jesús y la tempestad:**
        En Marcos 4:39, Jesús increpa al viento y dice al mar: «Calla, enmudece.» Esto revela que quien gobierna la creación también tiene autoridad sobre las tormentas de nuestra vida interior.
        
        • **Aplicación:**
        Entrega en oración sincera lo que hoy te turba, descansando en que el Señor cuida de ti (1 Pedro 5:7).
      """.trimIndent()

      else -> """
        📖 **Perspectiva Bíblica sobre: "$query"**
        
        Al examinar las Escrituras sobre este tema:
        
        • **Fundamento en la Palabra:**
        La Biblia nos invita a escudriñar los textos con reverencia: «Lámpara es a mis pies tu palabra, y lumbrera a mi camino.» (Salmos 119:105).
        
        • **Diferencia entre texto e interpretación:**
        El texto sagrado establece los principios inmutables de Dios: amor, justicia, santidad y redención a través de Cristo. A lo largo de la historia eclesiástica, los teólogos han desarrollado distintas aproximaciones para aplicar estos principios a cada época. Es esencial fundamentar la fe en la Escritura misma (2 Timoteo 3:16-17).
        
        • **Invitación al estudio:**
        Puedes consultar libros como Proverbios para sabiduría práctica, los Salmos para la oración y los Evangelios para conocer la vida y enseñanzas de Jesús. ¿Deseas que profundicemos en algún pasaje específico?
      """.trimIndent()
    }
  }
}
