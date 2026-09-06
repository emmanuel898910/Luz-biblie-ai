package com.example.data.repository

import com.example.data.model.QuizCategory
import com.example.data.model.QuizDifficulty
import com.example.data.model.QuizQuestion

class QuizRepository {

  val questions: List<QuizQuestion> = listOf(
    // Personajes
    QuizQuestion(
      id = 1,
      question = "¿Quién fue el profeta que fue llevado al cielo en un torbellino en un carro de fuego?",
      options = listOf("Eliseo", "Elías", "Isaías", "Jeremías"),
      correctOptionIndex = 1,
      explanation = "Elías fue arrebatado al cielo sin experimentar la muerte terrenal, y su manto cayó sobre su discípulo Eliseo.",
      biblicalReference = "2 Reyes 2:11",
      category = QuizCategory.PERSONAJES,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 2,
      question = "¿A cuál de los apóstoles se le reveló la visión apocalíptica en la isla de Patmos?",
      options = listOf("Pablo", "Pedro", "Juan", "Santiago"),
      correctOptionIndex = 2,
      explanation = "El apóstol Juan estuvo desterrado en la isla de Patmos 'por causa de la palabra de Dios', donde recibió la Revelación de Jesucristo.",
      biblicalReference = "Apocalipsis 1:9",
      category = QuizCategory.PERSONAJES,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 3,
      question = "¿Quién fue la reina que arriesgó su vida al presentarse ante el rey sin ser llamada para salvar a los judíos?",
      options = listOf("Vasti", "Ester", "Rut", "Débora"),
      correctOptionIndex = 1,
      explanation = "La reina Ester dijo: 'Y si perezco, que perezca', intercediendo con valentía por su pueblo ante el rey Asuero.",
      biblicalReference = "Ester 4:16",
      category = QuizCategory.PERSONAJES,
      difficulty = QuizDifficulty.MEDIO
    ),
    QuizQuestion(
      id = 4,
      question = "¿Cómo se llamaba el hombre de Chipre apodado 'Hijo de Consolación' que acompañó a Pablo?",
      options = listOf("Bernabé (José)", "Silas", "Timoteo", "Tito"),
      correctOptionIndex = 0,
      explanation = "Los apóstoles llamaron Bernabé a José el levita, lo cual traducido es 'Hijo de Consolación', conocido por su generosidad y respaldo a Pablo.",
      biblicalReference = "Hechos 4:36",
      category = QuizCategory.PERSONAJES,
      difficulty = QuizDifficulty.AVANZADO
    ),

    // Historias
    QuizQuestion(
      id = 5,
      question = "¿Cuántos días y noches llovió durante el diluvio en los días de Noé?",
      options = listOf("7 días", "30 días", "40 días y 40 noches", "100 días"),
      correctOptionIndex = 2,
      explanation = "La lluvia cayó sobre la faz de la tierra durante cuarenta días y cuarenta noches, cubriendo los montes más altos.",
      biblicalReference = "Génesis 7:12",
      category = QuizCategory.HISTORIAS,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 6,
      question = "¿Qué instrumento musical tocó David para calmar el espíritu afligido del rey Saúl?",
      options = listOf("Flauta", "Arpa", "Trompeta", "Címbalo"),
      correctOptionIndex = 1,
      explanation = "David tomaba el arpa y tocaba con su mano, y Saúl tenía alivio y estaba mejor, y el espíritu malo se apartaba de él.",
      biblicalReference = "1 Samuel 16:23",
      category = QuizCategory.HISTORIAS,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 7,
      question = "¿En qué río fue sanado el general Naamán tras sumergirse siete veces conforme a la palabra de Eliseo?",
      options = listOf("Río Éufrates", "Río Jordán", "Río Nilo", "Río Tigris"),
      correctOptionIndex = 1,
      explanation = "Naamán descendió y se lavó siete veces en el Jordán conforme a la palabra del varón de Dios, y su carne se volvió como la de un niño.",
      biblicalReference = "2 Reyes 5:14",
      category = QuizCategory.HISTORIAS,
      difficulty = QuizDifficulty.MEDIO
    ),
    QuizQuestion(
      id = 8,
      question = "¿Alrededor de qué ciudad marcharon los israelitas durante siete días antes de que cayeran sus muros?",
      options = listOf("Jericó", "Hai", "Gabaón", "Hebrón"),
      correctOptionIndex = 0,
      explanation = "Al séptimo día, tras dar siete vueltas y tocar las trompetas con gran clamor, los muros de Jericó cayeron por el poder de Dios.",
      biblicalReference = "Josué 6:20",
      category = QuizCategory.HISTORIAS,
      difficulty = QuizDifficulty.FACIL
    ),

    // Sabiduría
    QuizQuestion(
      id = 9,
      question = "Según Proverbios 1:7, ¿cuál es el principio de la sabiduría?",
      options = listOf(
        "El estudio de la filosofía",
        "El temor de Jehová",
        "La acumulación de riquezas",
        "La edad avanzada"
      ),
      correctOptionIndex = 1,
      explanation = "El reverente temor y adoración a Jehová es la puerta de entrada a todo entendimiento espiritual y vida recta.",
      biblicalReference = "Proverbios 1:7",
      category = QuizCategory.SABIDURIA,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 10,
      question = "En 1 Corintios 13, ¿cuál es la mayor de las virtudes eternas que nunca deja de ser?",
      options = listOf("La fe", "La esperanza", "El amor", "El don de lenguas"),
      correctOptionIndex = 2,
      explanation = "Pablo concluye que permanecen la fe, la esperanza y el amor, pero el mayor de ellos es el amor (ágape).",
      biblicalReference = "1 Corintios 13:13",
      category = QuizCategory.SABIDURIA,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 11,
      question = "¿Qué dice Santiago que es semejante a un pequeño timón que gobierna un gran barco?",
      options = listOf("El ojo", "La lengua", "El corazón", "Las manos"),
      correctOptionIndex = 1,
      explanation = "Santiago advierte que la lengua es un miembro pequeño pero capaz de desatar grandes incendios y guiar todo el cuerpo.",
      biblicalReference = "Santiago 3:4-5",
      category = QuizCategory.SABIDURIA,
      difficulty = QuizDifficulty.MEDIO
    ),

    // Evangelios
    QuizQuestion(
      id = 12,
      question = "¿Dónde realizó Jesús su primer milagro documentado convirtiendo el agua en vino?",
      options = listOf("Cafarnaúm", "Betania", "Caná de Galilea", "Nazaret"),
      correctOptionIndex = 2,
      explanation = "En las bodas de Caná de Galilea, Jesús manifestó su gloria y sus discípulos creyeron en él.",
      biblicalReference = "Juan 2:11",
      category = QuizCategory.EVANGELIOS,
      difficulty = QuizDifficulty.FACIL
    ),
    QuizQuestion(
      id = 13,
      question = "En la parábola del Buen Samaritano, ¿quiénes pasaron de largo antes de que el samaritano socorriera al herido?",
      options = listOf(
        "Un soldado romano y un comerciante",
        "Un sacerdote y un levita",
        "Un fariseo y un escriba",
        "Dos discípulos"
      ),
      correctOptionIndex = 1,
      explanation = "Tanto el sacerdote como el levita vieron al hombre herido al borde del camino y pasaron de largo por el otro lado.",
      biblicalReference = "Lucas 10:31-32",
      category = QuizCategory.EVANGELIOS,
      difficulty = QuizDifficulty.MEDIO
    ),
    QuizQuestion(
      id = 14,
      question = "¿Qué le respondió Jesús a Marta cuando ella se afanaba con muchos quehaceres?",
      options = listOf(
        "«Marta, apúrate con la cena»",
        "«Marta, Marta, afanada y turbada estás con muchas cosas. Pero sólo una cosa es necesaria...»",
        "«Debes reprender a tu hermana María»",
        "«El trabajo manual es más importante que la oración»"
      ),
      correctOptionIndex = 1,
      explanation = "Jesús destacó que María había escogido 'la buena parte', la cual no le sería quitada: escuchar a los pies del Señor.",
      biblicalReference = "Lucas 10:41-42",
      category = QuizCategory.EVANGELIOS,
      difficulty = QuizDifficulty.MEDIO
    )
  )

  fun getQuestionsByCategory(category: QuizCategory?): List<QuizQuestion> {
    return if (category == null) questions else questions.filter { it.category == category }
  }
}
