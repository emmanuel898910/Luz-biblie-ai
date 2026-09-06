package com.example.data.repository

import com.example.data.model.MemorizationVerse

class MemoryRepository {

  val versesToMemorize: List<MemorizationVerse> = listOf(
    MemorizationVerse(
      id = "fil_4_13",
      reference = "Filipenses 4:13",
      fullText = "Todo lo puedo en Cristo que me fortalece.",
      theme = "Fortaleza"
    ),
    MemorizationVerse(
      id = "sal_23_1",
      reference = "Salmos 23:1",
      fullText = "Jehová es mi pastor; nada me faltará.",
      theme = "Provisión y Paz"
    ),
    MemorizationVerse(
      id = "jua_3_16",
      reference = "Juan 3:16",
      fullText = "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna.",
      theme = "Amor y Salvación"
    ),
    MemorizationVerse(
      id = "jos_1_9",
      reference = "Josué 1:9",
      fullText = "Mira que te mando que te esfuerces y seas valiente; no temas ni desmayes, porque Jehová tu Dios estará contigo en dondequiera que fueres.",
      theme = "Valentía"
    ),
    MemorizationVerse(
      id = "pro_3_5",
      reference = "Proverbios 3:5",
      fullText = "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia.",
      theme = "Confianza"
    ),
    MemorizationVerse(
      id = "rom_8_28",
      reference = "Romanos 8:28",
      fullText = "Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados.",
      theme = "Propósito"
    ),
    MemorizationVerse(
      id = "sal_119_105",
      reference = "Salmos 119:105",
      fullText = "Lámpara es a mis pies tu palabra, y lumbrera a mi camino.",
      theme = "Guía divina"
    )
  )

  /**
   * Helper to split verse into scrambled words for the interactive ordering challenge
   */
  fun getScrambledWords(verse: MemorizationVerse): Pair<List<String>, List<String>> {
    val cleanWords = verse.fullText
      .replace(",", "")
      .replace(".", "")
      .replace(";", "")
      .replace(":", "")
      .replace("«", "")
      .replace("»", "")
      .split("\\s+".toRegex())
      .filter { it.isNotBlank() }

    val scrambled = cleanWords.shuffled()
    return Pair(cleanWords, scrambled)
  }
}
