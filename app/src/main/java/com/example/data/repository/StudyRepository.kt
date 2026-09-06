package com.example.data.repository

import com.example.data.model.StudyTopic

class StudyRepository {

  val studyTopics: List<StudyTopic> = listOf(
    StudyTopic(
      id = "paz_tormenta",
      title = "Paz en la Tormenta",
      description = "Cómo encontrar serenidad y firmeza cuando las circunstancias de la vida amenazan nuestra estabilidad.",
      iconEmoji = "🕊️",
      verses = listOf(
        "Filipenses 4:6-7" to "«Por nada estéis afanosos; sino sean notorias vuestras peticiones delante de Dios en toda oración y ruego, con hacimiento de gracias. Y la paz de Dios, que sobrepuja todo entendimiento, guardará vuestros corazones y vuestros entendimientos en Cristo Jesús.»",
        "Juan 14:27" to "«La paz os dejo, mi paz os doy; no como el mundo la da, yo os la doy. No se turbe vuestro corazón, ni tenga miedo.»",
        "Salmos 46:1-2" to "«Dios es nuestro amparo y fortaleza, nuestro pronto auxilio en las tribulaciones. Por tanto, no temeremos, aunque la tierra sea removida...»"
      ),
      biblicalContext = "Pablo redactó la carta a los Filipenses encadenado en una prisión imperial romana. Lejos de quejarse o caer en amargura, su mayor exhortación es el gozo y la paz que no dependen de la celda terrenal, sino de la soberanía de Cristo.",
      theologicalExplanation = "La paz bíblica ('Shalom') trasciende el concepto secular de serenidad psicológica o ausencia de ruidos. Es un estado de reconciliación integral con Dios mediante el sacrificio de la Cruz, produciendo una confianza inconmovible en su cuidado providencial.",
      reflectionQuestions = listOf(
        "¿Qué situación actual te roba la paz y te genera ansiedad constante?",
        "¿Has convertido tus preocupaciones en oraciones con acción de gracias o solo en quejas internas?",
        "¿Cómo se manifiesta en tus decisiones diarias la certeza de que Dios tiene el control supremo?"
      ),
      practicalApplication = "Dedica 10 minutos al despertar a entregar en voz alta cada motivo de preocupación al Señor. Escribe tres cosas específicas por las cuales darle gracias antes de pedir cualquier otra cosa.",
      closingPrayer = "Señor Dios todopoderoso, en medio de los vientos que sacuden mi barca, elijo hoy no mirar las olas sino fijar mis ojos en ti. Derrama tu paz sobrenatural sobre mis pensamientos y aquieta mi corazón. En el nombre de Jesús, Amén."
    ),

    StudyTopic(
      id = "amor_perdon",
      title = "El Amor y el Perdón",
      description = "El corazón del Evangelio: sanar rencores a través del amor incondicional recibido de Cristo.",
      iconEmoji = "❤️",
      verses = listOf(
        "Colosenses 3:13" to "«Soportándoos con paciencia los unos a los otros, y perdonándoos los unos a los otros si alguno tuviere queja contra otro. De la manera que Cristo os perdonó, así también hacedlo vosotros.»",
        "1 Corintios 13:4-5" to "«El amor es sufrido, es benigno; el amor no tiene envidia, el amor no es jactancioso, no se envanece; no hace nada indebido, no busca lo suyo, no se irrita, no piensa el mal...»",
        "Mateo 6:14-15" to "«Porque si perdonáis a los hombres sus ofensas, os perdonará también a vosotros vuestro Padre celestial.»"
      ),
      biblicalContext = "La sociedad del siglo primero regida por el honor y la venganza (ley del talión o justicia retributiva) consideraba el perdón como debilidad. Jesús y los apóstoles trastocaron el mundo al proclamar que el mayor poder reside en amar a los enemigos y perdonar sin límites.",
      theologicalExplanation = "El perdón no es negar el daño causado ni esperar que la emoción herida desaparezca instantáneamente; es un acto de obediencia voluntaria que renuncia al derecho de la venganza personal, confiando el juicio a Dios y reconociendo cuánto fuimos perdonados por la gracia divina.",
      reflectionQuestions = listOf(
        "¿Hay alguna persona hacia quien guardas resentimiento o deseas en secreto que le vaya mal?",
        "¿De qué manera recordar el perdón inmerecido de Cristo en la Cruz ablanda tu postura?",
        "¿Qué pasos prácticos de reconciliación o bendición puedes dar hoy hacia quien te hirió?"
      ),
      practicalApplication = "Escribe una carta de perdón sincero ante Dios hacia esa persona que te lastimó. Ora activamente pidiendo la bendición de Dios sobre su vida durante 7 días seguidos.",
      closingPrayer = "Padre amoroso, reconozco la inmensidad de tu perdón hacia mis propias transgresiones. Dame la gracia y humildad para soltar toda raíz de rencor y amargura. Deseo que mi corazón sea reflejo de tu amor restaurador. Amén."
    ),

    StudyTopic(
      id = "fe_inquebrantable",
      title = "La Fe Inquebrantable",
      description = "Confiar en las promesas divinas aún cuando los sentidos físicos y las circunstancias digan lo contrario.",
      iconEmoji = "⚓",
      verses = listOf(
        "Hebreos 11:1" to "«Es, pues, la fe la certeza de lo que se espera, la convicción de lo que no se ve.»",
        "Hebreos 11:6" to "«Pero sin fe es imposible agradar a Dios; porque es necesario que el que se acerca a Dios crea que le hay, y que es galardonador de los que le buscan.»",
        "2 Corintios 5:7" to "«Porque por fe andamos, no por vista.»",
        "Romanos 10:17" to "«Así que la fe es por el oír, y el oír, por la palabra de Dios.»"
      ),
      biblicalContext = "La epístola a los Hebreos fue dirigida a cristianos judíos que enfrentaban dura presión social, despojo de bienes y tentación de retroceder hacia antiguos ritos. El autor les recuerda a los gigantes de la fe que miraron más allá de lo temporal hacia la ciudad eterna.",
      theologicalExplanation = "La fe bíblica no es credulidad ciega ni pensamiento positivo mágico. Es la convicción razonada y firme en el carácter inmutable de Dios y en la fiabilidad absoluta de sus promesas reveladas en las Escrituras.",
      reflectionQuestions = listOf(
        "¿En qué área de tu vida estás caminando más por vista que por fe?",
        "¿Cómo alimentas tu fe diariamente a través de la lectura atenta de la Palabra?",
        "¿Qué paso de obediencia estás postergando por temor a lo desconocido?"
      ),
      practicalApplication = "Identifica una promesa bíblica vinculada a tu mayor batalla actual. Memorízala, medítala tres veces al día y actúa con valentía como si Dios ya estuviera obrando tras bambalinas.",
      closingPrayer = "Señor Jesús, creo, pero aumenta mi fe en momentos de flaqueza. Ayúdame a caminar firme sostenido de tu mano, recordando que tú eres fiel en cumplir todo lo que has prometido. En tu nombre, Amén."
    ),

    StudyTopic(
      id = "sabiduria_decisiones",
      title = "Sabiduría para Decidir",
      description = "Principios bíblicos para discernir la voluntad de Dios y tomar decisiones sabias en la vida diaria.",
      iconEmoji = "🧭",
      verses = listOf(
        "Santiago 1:5" to "«Y si alguno de vosotros tiene falta de sabiduría, pídala a Dios, el cual da a todos abundantemente y sin reproche, y le será dada.»",
        "Proverbios 3:5-6" to "«Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia. Reconócelo en todos tus caminos, y él enderezará tus veredas.»",
        "Salmos 119:105" to "«Lámpara es a mis pies tu palabra, y lumbrera a mi camino.»"
      ),
      biblicalContext = "El libro de Proverbios y la epístola de Santiago conforman pilares de la literatura sapiencial bíblica, enseñando que la verdadera inteligencia moral se demuestra en la conducta justa, el dominio propio y el temor reverente al Creador.",
      theologicalExplanation = "La sabiduría bíblica ('Jojmá') no es mera erudición teórica, sino la habilidad divina aplicada para vivir conforme al diseño y justicia de Dios en un mundo quebrantado.",
      reflectionQuestions = listOf(
        "¿Sueles consultar primero tus impulsos o la Palabra de Dios antes de decidir?",
        "¿Buscas consejo en personas maduras en la fe o solo en quienes aprueban tus deseos?",
        "¿Estás dispuesto a obedecer a Dios incluso si su dirección contradice la opinión popular?"
      ),
      practicalApplication = "Antes de tomar una decisión relevante esta semana, busca al menos dos textos bíblicos afines, ora pidiendo sabiduría sin dudar y consulta el parecer de un hermano maduro en la fe.",
      closingPrayer = "Señor y Dios mío, reconozco que mis propios caminos son limitados y engañosos. Lléname de tu Espíritu Santo, alumbra mis pasos con tu Palabra y dame el valor de obedecer tu dirección. Amén."
    )
  )
}
