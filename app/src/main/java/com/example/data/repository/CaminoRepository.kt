package com.example.data.repository

import com.example.data.model.CaminoStatus
import com.example.data.model.CaminoStep

class CaminoRepository {

  val steps: List<CaminoStep> = listOf(
    CaminoStep(
      id = 1,
      order = 1,
      title = "La Creación y el Origen",
      subtitle = "Génesis 1 - 3",
      scriptureFocus = "Génesis 1:1, 1:27, 3:15",
      summary = "Dios crea el universo por el poder de su palabra y promete redención desde el inicio de la historia humana.",
      lessonText = "En el principio, Dios creó los cielos y la tierra en orden y perfección. Creó al ser humano a su imagen y semejanza con dignidad y propósito sagrado. Aunque la caída trajo separación, Dios emitió la primera promesa mesiánica (el 'protoevangelio' en Génesis 3:15): la simiente de la mujer heriría en la cabeza a la serpiente.",
      keyVerse = "«En el principio crió Dios los cielos y la tierra.» (Génesis 1:1)",
      checkpointQuestion = "¿Qué anunció Dios en Génesis 3:15 como primera promesa de salvación?",
      checkpointOptions = listOf(
        "Que construirían una gran torre",
        "Que la simiente de la mujer heriría a la serpiente",
        "Que no volvería a llover sobre la tierra",
        "Que Israel tendría reyes humanos"
      ),
      checkpointCorrectIndex = 1,
      rewardXp = 50,
      status = CaminoStatus.IN_PROGRESS
    ),
    CaminoStep(
      id = 2,
      order = 2,
      title = "El Éxodo y la Libertad",
      subtitle = "Éxodo 12 - 20",
      scriptureFocus = "Éxodo 14:13-14, 20:1-17",
      summary = "La liberación sobrenatural de la esclavitud en Egipto y la promulgación de los Diez Mandamientos en el Sinaí.",
      lessonText = "Dios oyó el clamor de su pueblo y levantó a Moisés. Mediante la Pascua (cordero sin defecto cuya sangre protegía los hogares) y la apertura del Mar Rojo, Dios enseñó que la redención es una obra soberana. En el Sinaí entregó la Ley no para ganar el rescate, sino como guía de vida santa para un pueblo ya liberado.",
      keyVerse = "«Jehová peleará por vosotros, y vosotros estaréis tranquilos.» (Éxodo 14:14)",
      checkpointQuestion = "¿Qué debía ponerse en los postes y dinteles de las casas durante la primera Pascua?",
      checkpointOptions = listOf(
        "Una marca de aceite",
        "La sangre de un cordero sin defecto",
        "Una vara de almendro",
        "Agua del río Nilo"
      ),
      checkpointCorrectIndex = 1,
      rewardXp = 60,
      status = CaminoStatus.LOCKED
    ),
    CaminoStep(
      id = 3,
      order = 3,
      title = "El Corazón de la Alabanza",
      subtitle = "Salmos y Sabiduría",
      scriptureFocus = "Salmos 23, 51, 103",
      summary = "Los cánticos del alma creyente: cómo adorar a Dios en gozo, dolor, arrepentimiento y alabanza.",
      lessonText = "Los Salmos son la escuela de la oración bíblica. Nos enseñan a presentar todas nuestras emociones ante Dios con reverente honestidad. Desde el reposo del Salmo 23 («Jehová es mi pastor») hasta el quebranto arrepentido de David en el Salmo 51 («Crea en mí, oh Dios, un corazón limpio»), las Escrituras nos guían a descansar en la misericordia divina.",
      keyVerse = "«Crea en mí, oh Dios, un corazón limpio, y renueva un espíritu recto dentro de mí.» (Salmos 51:10)",
      checkpointQuestion = "¿Qué pide el salmista a Dios en el Salmo 51 tras reconocer su pecado?",
      checkpointOptions = listOf(
        "Grandes riquezas y palacios",
        "Venganza contra sus enemigos",
        "Un corazón limpio y un espíritu recto",
        "Un carro de fuego para subir al cielo"
      ),
      checkpointCorrectIndex = 2,
      rewardXp = 75,
      status = CaminoStatus.LOCKED
    ),
    CaminoStep(
      id = 4,
      order = 4,
      title = "El Sermón del Monte",
      subtitle = "Mateo 5 - 7",
      scriptureFocus = "Mateo 5:3-16, 6:33",
      summary = "El manifiesto del Reino de Dios proclamado por Jesús: las bienaventuranzas y la vida en la luz.",
      lessonText = "Jesús asciende al monte y enseña con autoridad divina los valores contraculturales de su Reino: los bienaventurados son los humildes de espíritu, los pacificadores, los que tienen hambre y sed de justicia. Llama a sus discípulos a ser la sal de la tierra y la luz del mundo, confiando el sustento diario al Padre celestial y buscando primeramente su Reino.",
      keyVerse = "«Mas buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas.» (Mateo 6:33)",
      checkpointQuestion = "Según Jesús en Mateo 6:33, ¿qué debemos buscar primeramente?",
      checkpointOptions = listOf(
        "Aprobación de los hombres",
        "El reino de Dios y su justicia",
        "Comodidad y riquezas temporales",
        "Títulos religiosos"
      ),
      checkpointCorrectIndex = 1,
      rewardXp = 90,
      status = CaminoStatus.LOCKED
    ),
    CaminoStep(
      id = 5,
      order = 5,
      title = "La Cruz y la Resurrección",
      subtitle = "Evangelio de Juan 19 - 21",
      scriptureFocus = "Juan 19:30, 20:19-29, Romanos 5:8",
      summary = "El sacrificio expiatorio de Jesús, la tumba vacía y la victoria definitiva sobre el pecado y la muerte.",
      lessonText = "En la Cruz del Calvario, Jesús exclamó: «Consumado es» (*Tetelestai*), saldando por completo la deuda del pecado de la humanidad. Al tercer día resucitó triunfante, apareciéndose a sus discípulos y comisionándolos a llevar las buenas nuevas a toda criatura con la promesa de su presencia continua.",
      keyVerse = "«Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros.» (Romanos 5:8)",
      checkpointQuestion = "¿Qué palabra pronunció Jesús en la Cruz al consumar la obra de salvación?",
      checkpointOptions = listOf(
        "«¿Por qué me habéis dejado?»",
        "«Consumado es»",
        "«Pedid señales»",
        "«Volveré a Nazaret»"
      ),
      checkpointCorrectIndex = 1,
      rewardXp = 100,
      status = CaminoStatus.LOCKED
    ),
    CaminoStep(
      id = 6,
      order = 6,
      title = "La Nueva Creación y la Esperanza",
      subtitle = "Apocalipsis 21 - 22",
      scriptureFocus = "Apocalipsis 21:1-5, 22:20",
      summary = "El cumplimiento final del plan divino: la morada de Dios con los hombres y la renovación de todas las cosas.",
      lessonText = "La historia bíblica concluye no en destrucción, sino en consumación gloriosa. El apóstol Juan contempla un cielo nuevo y una tierra nueva, donde Dios enjugará toda lágrima y no habrá más muerte ni dolor. La Iglesia clama con fe: «¡Amén! Sí, ven, Señor Jesús».",
      keyVerse = "«Y enjugará Dios toda lágrima de los ojos de ellos; y ya no habrá muerte, ni habrá más llanto...» (Apocalipsis 21:4)",
      checkpointQuestion = "¿Qué promete Dios en Apocalipsis 21:4 respecto al dolor humano?",
      checkpointOptions = listOf(
        "Que continuará por siempre",
        "Que solo los reyes no llorarán",
        "Que enjugará toda lágrima y no habrá más muerte ni llanto",
        "Que olvidaremos quiénes somos"
      ),
      checkpointCorrectIndex = 2,
      rewardXp = 120,
      status = CaminoStatus.LOCKED
    )
  )
}
