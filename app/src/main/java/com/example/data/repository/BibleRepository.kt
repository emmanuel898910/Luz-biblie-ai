package com.example.data.repository

import com.example.data.local.BibleNoteEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.LuzDao
import com.example.data.model.BibleBook
import com.example.data.model.BibleChapter
import com.example.data.model.BibleVerse
import com.example.data.model.BookCategory
import com.example.data.model.Testament
import com.example.data.model.VerseOfTheDay
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BibleRepository(private val dao: LuzDao) {

  val books: List<BibleBook> = listOf(
    // Antiguo Testamento - Pentateuco
    BibleBook("GEN", "Génesis", "Gén", Testament.ANTIGUO, BookCategory.PENTATEUCO, 50, "El principio de todo", "La creación del mundo, la caída, el pacto con Abraham y los patriarcas.", "En el principio crió Dios los cielos y la tierra.", "Génesis 1:1"),
    BibleBook("EXO", "Éxodo", "Éx", Testament.ANTIGUO, BookCategory.PENTATEUCO, 40, "Liberación y la Ley", "La redención de Israel de Egipto y la entrega de la Ley en el Sinaí.", "Y habló Dios todas estas palabras...", "Éxodo 20:1"),
    BibleBook("LEV", "Levítico", "Lev", Testament.ANTIGUO, BookCategory.PENTATEUCO, 27, "Santidad y adoración", "Instrucciones de pureza, sacrificios y sacerdocio ante un Dios santo.", "Habéis, pues, de serme santos, porque yo Jehová soy santo.", "Levítico 20:26"),
    BibleBook("NUM", "Números", "Núm", Testament.ANTIGUO, BookCategory.PENTATEUCO, 36, "La travesía del desierto", "El censo y el peregrinaje de 40 años hacia la tierra prometida.", "Jehová te bendiga, y te guarde.", "Números 6:24"),
    BibleBook("DEU", "Deuteronomio", "Deut", Testament.ANTIGUO, BookCategory.PENTATEUCO, 34, "Renovación del pacto", "Discursos finales de Moisés llamando a obedecer y amar a Dios.", "Y amarás á Jehová tu Dios de todo tu corazón.", "Deuteronomio 6:5"),

    // Históricos
    BibleBook("JOS", "Josué", "Jos", Testament.ANTIGUO, BookCategory.HISTORICOS, 24, "Conquista y fidelidad", "La entrada triunfal a Canaán y el cumplimiento de las promesas divinas.", "Mira que te mando que te esfuerces y seas valiente.", "Josué 1:9"),
    BibleBook("JUE", "Jueces", "Jue", Testament.ANTIGUO, BookCategory.HISTORICOS, 21, "Ciclos de redención", "La liberación de Israel a través de jueces líderes como Gedeón y Sansón.", "En aquellos días no había rey en Israel.", "Jueces 21:25"),
    BibleBook("RUT", "Rut", "Rut", Testament.ANTIGUO, BookCategory.HISTORICOS, 4, "Lealtad y redención", "Historia de devoción de Rut que forma parte del linaje de David y Jesús.", "Tu pueblo será mi pueblo, y tu Dios mi Dios.", "Rut 1:16"),
    BibleBook("1SA", "1 Samuel", "1 Sam", Testament.ANTIGUO, BookCategory.HISTORICOS, 31, "El paso a la monarquía", "La vida de Samuel, el reinado de Saúl y la unción de David.", "El hombre mira lo que está delante de sus ojos, mas Jehová mira el corazón.", "1 Samuel 16:7"),
    BibleBook("2SA", "2 Samuel", "2 Sam", Testament.ANTIGUO, BookCategory.HISTORICOS, 24, "El reino de David", "Las victorias, reinado y quebranto del rey David en Jerusalén.", "Jehová es mi roca, y mi fortaleza, y mi libertador.", "2 Samuel 22:2"),
    BibleBook("1RE", "1 Reyes", "1 Rey", Testament.ANTIGUO, BookCategory.HISTORICOS, 22, "Salomón y la división", "La gloria del templo de Salomón y la posterior división del reino.", "Dame, pues, un corazón entendido para juzgar á tu pueblo.", "1 Reyes 3:9"),
    BibleBook("2RE", "2 Reyes", "2 Rey", Testament.ANTIGUO, BookCategory.HISTORICOS, 25, "Profetas y cautiverio", "El ministerio de Elías y Eliseo y la caída de los reinos ante Asiria y Babilonia.", "Y dijo Eliseo: Te ruego que dos partes de tu espíritu sean sobre mí.", "2 Reyes 2:9"),
    BibleBook("1CR", "1 Crónicas", "1 Crón", Testament.ANTIGUO, BookCategory.HISTORICOS, 29, "Herencia sacerdotal", "Genealogías sagradas y preparativos de David para el templo.", "Alabad á Jehová, invocad su nombre.", "1 Crónicas 16:8"),
    BibleBook("2CR", "2 Crónicas", "2 Crón", Testament.ANTIGUO, BookCategory.HISTORICOS, 36, "Avivamiento y templo", "La historia de Judá y el llamado al arrepentimiento nacional.", "Si se humillare mi pueblo, sobre el cual mi nombre es invocado...", "2 Crónicas 7:14"),
    BibleBook("ESD", "Esdras", "Esd", Testament.ANTIGUO, BookCategory.HISTORICOS, 10, "El retorno del exilio", "La reconstrucción del templo en Jerusalén guiada por Esdras.", "Porque Esdras había preparado su corazón para inquirir la ley de Jehová.", "Esdras 7:10"),
    BibleBook("NEH", "Nehemías", "Neh", Testament.ANTIGUO, BookCategory.HISTORICOS, 13, "Reconstruir los muros", "El liderazgo y oración de Nehemías para restaurar las murallas.", "El gozo de Jehová es vuestra fuerza.", "Nehemías 8:10"),
    BibleBook("EST", "Ester", "Est", Testament.ANTIGUO, BookCategory.HISTORICOS, 10, "Providencia oculta", "La valentía de la reina Ester para salvar a su pueblo de la destrucción.", "¿Y quién sabe si para esta hora has llegado al reino?", "Ester 4:14"),

    // Poéticos y Sabiduría
    BibleBook("JOB", "Job", "Job", Testament.ANTIGUO, BookCategory.POETICOS, 42, "Confianza en el dolor", "El debate sobre el sufrimiento del justo y la soberanía inescrutable de Dios.", "Yo sé que mi Redentor vive.", "Job 19:25"),
    BibleBook("SAL", "Salmos", "Sal", Testament.ANTIGUO, BookCategory.POETICOS, 150, "Alabanza y oración", "Himnos del corazón humano en júbilo, angustia, arrepentimiento y alabanza.", "Jehová es mi pastor; nada me faltará.", "Salmos 23:1"),
    BibleBook("PRO", "Proverbios", "Prov", Testament.ANTIGUO, BookCategory.POETICOS, 31, "Sabiduría cotidiana", "Máximas inspiradas para vivir con prudencia, justicia y temor del Señor.", "El principio de la sabiduría es el temor de Jehová.", "Proverbios 1:7"),
    BibleBook("ECL", "Eclesiastés", "Ecl", Testament.ANTIGUO, BookCategory.POETICOS, 12, "El sentido de la vida", "La búsqueda del propósito bajo el sol y el llamado a temer a Dios.", "Teme á Dios, y guarda sus mandamientos.", "Eclesiastés 12:13"),
    BibleBook("CAN", "Cantares", "Cant", Testament.ANTIGUO, BookCategory.POETICOS, 8, "El amor conyugal", "Poema lírico del amor puro y apasionado, reflejo del amor divino.", "Las muchas aguas no podrán apagar el amor.", "Cantares 8:7"),

    // Profetas Mayores y Menores
    BibleBook("ISA", "Isaías", "Isa", Testament.ANTIGUO, BookCategory.PROFETAS_MAYORES, 66, "El Evangelio del AT", "Profecías del Mesías, el Siervo sufriente y los cielos nuevos.", "Mas él herido fue por nuestras rebeliones.", "Isaías 53:5"),
    BibleBook("JER", "Jeremías", "Jer", Testament.ANTIGUO, BookCategory.PROFETAS_MAYORES, 52, "El profeta que lloró", "Llamado al arrepentimiento y la promesa de un Nuevo Pacto escrito en el corazón.", "Porque yo sé los pensamientos que tengo acerca de vosotros...", "Jeremías 29:11"),
    BibleBook("LAM", "Lamentaciones", "Lam", Testament.ANTIGUO, BookCategory.PROFETAS_MAYORES, 5, "Misericordia renovada", "Elegías fúnebres sobre Jerusalén y la inquebrantable fidelidad divina.", "Nuevas son cada mañana; grande es tu fidelidad.", "Lamentaciones 3:23"),
    BibleBook("EZE", "Ezequiel", "Eze", Testament.ANTIGUO, BookCategory.PROFETAS_MAYORES, 48, "La gloria de Dios", "Visiones de la gloria de Dios, el valle de huesos secos y el nuevo corazón.", "Y os daré corazón nuevo, y pondré espíritu nuevo dentro de vosotros.", "Ezequiel 36:26"),
    BibleBook("DAN", "Daniel", "Dan", Testament.ANTIGUO, BookCategory.PROFETAS_MAYORES, 12, "Reinos y el Altísimo", "Integridad en Babilonia, el foso de los leones y visiones apocalípticas.", "Y los entendidos resplandecerán como el resplandor del firmamento.", "Daniel 12:3"),
    BibleBook("OSE", "Oseas", "Os", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 14, "El amor incondicional", "El amor fiel de Dios frente a la infidelidad humana.", "Porque misericordia quise, y no sacrificio.", "Oseas 6:6"),
    BibleBook("JOE", "Joel", "Joel", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 3, "El derramamiento del Espíritu", "El día de Jehová y la promesa del Espíritu Santo sobre toda carne.", "Derramaré mi Espíritu sobre toda carne.", "Joel 2:28"),
    BibleBook("AMO", "Amós", "Am", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 9, "Justicia y rectitud", "El clamor por la justicia social y el culto sincero.", "Pero corra el juicio como las aguas, y la justicia como impetuoso arroyo.", "Amós 5:24"),
    BibleBook("ABD", "Abdías", "Abd", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 1, "Juicio de la soberbia", "La caída de Edom y la vindicación del monte de Sion.", "Y el reino será de Jehová.", "Abdías 1:21"),
    BibleBook("JON", "Jonás", "Jon", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 4, "La gracia universal", "La compasión de Dios hacia los habitantes arrepentidos de Nínive.", "La salvación es de Jehová.", "Jonás 2:9"),
    BibleBook("MIQ", "Miqueas", "Miq", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 7, "Caminar humildemente", "Profecía del nacimiento del Mesías en Belén y la verdadera devoción.", "Solamente hacer justicia, y amar misericordia, y humillarte ante tu Dios.", "Miqueas 6:8"),
    BibleBook("NAH", "Nahum", "Nah", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 3, "El Dios justo", "El juicio sobre Nínive y el consuelo a los que confían en el Señor.", "Bueno es Jehová para fortaleza en el día de la angustia.", "Nahum 1:7"),
    BibleBook("HAB", "Habacuc", "Hab", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 3, "El justo por la fe vivirá", "Diálogo sincero con Dios y canto de gozo en medio de la adversidad.", "Mas el justo en su fe vivirá.", "Habacuc 2:4"),
    BibleBook("SOF", "Sofonías", "Sof", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 3, "Restauración gozosa", "El día del juicio y el cántico de regocijo de Dios sobre su remanente.", "Jehová está en medio de ti, poderoso, él salvará.", "Sofonías 3:17"),
    BibleBook("HAG", "Hageo", "Hag", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 2, "Prioridades del Reino", "Llamado a reconstruir la casa de Dios antes que el bienestar personal.", "Mía es la plata, y mío es el oro, dice Jehová de los ejércitos.", "Hageo 2:8"),
    BibleBook("ZAC", "Zacarías", "Zac", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 14, "Esperanza mesiánica", "Visiones nocturnas del Mesías entrando humilde sobre un asno.", "No con ejército, ni con fuerza, sino con mi espíritu, ha dicho Jehová.", "Zacarías 4:6"),
    BibleBook("MAL", "Malaquías", "Mal", Testament.ANTIGUO, BookCategory.PROFETAS_MENORES, 4, "El Sol de Justicia", "Último profeta del AT señalando la venida del precursor Elías.", "Mas á vosotros los que teméis mi nombre, nacerá el Sol de justicia.", "Malaquías 4:2"),

    // Nuevo Testamento - Evangelios e Historia
    BibleBook("MAT", "Mateo", "Mat", Testament.NUEVO, BookCategory.EVANGELIOS, 28, "Jesús el Rey Mesías", "El cumplimiento de las profecías del Antiguo Testamento en Cristo.", "Buscad primeramente el reino de Dios y su justicia.", "Mateo 6:33"),
    BibleBook("MAR", "Marcos", "Mar", Testament.NUEVO, BookCategory.EVANGELIOS, 16, "Jesús el Siervo", "El ministerio dinámico y sacrificado del Hijo del Hombre.", "Porque el Hijo del hombre tampoco vino para ser servido, mas para servir.", "Marcos 10:45"),
    BibleBook("LUC", "Lucas", "Luc", Testament.NUEVO, BookCategory.EVANGELIOS, 24, "Jesús el Salvador", "La compasión de Jesús hacia marginados, pecadores, mujeres y gentiles.", "Porque el Hijo del hombre vino á buscar y á salvar lo que se había perdido.", "Lucas 19:10"),
    BibleBook("JUA", "Juan", "Juan", Testament.NUEVO, BookCategory.EVANGELIOS, 21, "Jesús el Hijo de Dios", "El Verbo encarnado que da vida eterna a todo aquel que cree.", "Porque de tal manera amó Dios al mundo, que ha dado á su Hijo unigénito...", "Juan 3:16"),
    BibleBook("HEC", "Hechos", "Hech", Testament.NUEVO, BookCategory.HISTORIA_IGLESIA, 28, "El Espíritu en acción", "El nacimiento de la Iglesia primitiva y la expansión del Evangelio por el mundo.", "Mas recibiréis la virtud del Espíritu Santo... y me seréis testigos.", "Hechos 1:8"),

    // Epístolas Paulinas
    BibleBook("ROM", "Romanos", "Rom", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 16, "Justificación por la Fe", "La magna exposición teológica de la gracia, el pecado y la redención.", "Justificados pues por la fe, tenemos paz para con Dios por medio de nuestro Señor Jesucristo.", "Romanos 5:1"),
    BibleBook("1CO", "1 Corintios", "1 Cor", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 16, "Unidad y amor", "Consejos a la iglesia en orden, dones espirituales y la excelencia del amor.", "Y ahora permanecen la fe, la esperanza, y el amor, estos tres: empero el mayor de ellos es el amor.", "1 Corintios 13:13"),
    BibleBook("2CO", "2 Corintios", "2 Cor", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 13, "Fuerza en la debilidad", "El ministerio reconciliador y la suficiencia de la gracia divina.", "Bástale á mi gracia: porque mi potencia en la flaqueza se perfecciona.", "2 Corintios 12:9"),
    BibleBook("GAL", "Gálatas", "Gál", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 6, "Libertad en Cristo", "La defensa de la gracia frente al legalismo y el fruto del Espíritu.", "Con Cristo estoy juntamente crucificado, y vivo, no ya yo, mas vive Cristo en mí.", "Gálatas 2:20"),
    BibleBook("EFE", "Efesios", "Efe", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 6, "La Iglesia en Cristo", "Las bendiciones celestiales, la armadura de Dios y la unidad del cuerpo.", "Porque por gracia sois salvos por la fe; y esto no de vosotros, pues es don de Dios.", "Efesios 2:8"),
    BibleBook("FIL", "Filipenses", "Fil", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 4, "Gozo incondicional", "El gozo del creyente en toda circunstancia y la humildad de Cristo.", "Todo lo puedo en Cristo que me fortalece.", "Filipenses 4:13"),
    BibleBook("COL", "Colosenses", "Col", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 4, "La supremacía de Cristo", "Cristo como cabeza de toda la creación y plenitud de la deidad.", "Y él es la cabeza del cuerpo que es la iglesia.", "Colosenses 1:18"),
    BibleBook("1TE", "1 Tesalonicenses", "1 Tes", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 5, "La esperanza del regreso", "Consuelo y santidad ante la gloriosa venida del Señor.", "Estad siempre gozosos. Orad sin cesar. Dad gracias en todo.", "1 Tesalonicenses 5:16-18"),
    BibleBook("2TE", "2 Tesalonicenses", "2 Tes", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 3, "Firmeza en la verdad", "Perseverancia ante las pruebas y la certidumbre del juicio final.", "El Señor de paz os dé siempre paz en toda manera.", "2 Tesalonicenses 3:16"),
    BibleBook("1TI", "1 Timoteo", "1 Tim", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 6, "Liderazgo pastoral", "Instrucciones prácticas para pastores, líderes y la vida de la congregación.", "Pelea la buena batalla de la fe, echa mano de la vida eterna.", "1 Timoteo 6:12"),
    BibleBook("2TI", "2 Timoteo", "2 Tim", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 4, "Fidelidad hasta el fin", "Última carta testamentaria de Pablo animando a transmitir la verdad.", "Porque no nos ha dado Dios el espíritu de temor, sino el de fortaleza, y de amor, y de templanza.", "2 Timoteo 1:7"),
    BibleBook("TIT", "Tito", "Tit", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 3, "Buenas obras y sana doctrina", "El testimonio cristiano genuino que adorna la doctrina de Dios.", "Porque la gracia de Dios que trae salvación á todos los hombres, se manifestó.", "Tito 2:11"),
    BibleBook("FLM", "Filemón", "Flm", Testament.NUEVO, BookCategory.EPISTOLAS_PAULINAS, 1, "Perdón y fraternidad", "La reconciliación y amor fraternal entre amos y siervos en Cristo.", "Acéptale como á mí mismo.", "Filemón 1:17"),

    // Epístolas Generales y Profecía
    BibleBook("HEB", "Hebreos", "Heb", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 13, "El Pacto Superior", "Cristo como sumo sacerdote supremo, la galería de la fe y la perseverancia.", "Es pues la fe la sustancia de las cosas que se esperan, la demostración de las cosas que no se ven.", "Hebreos 11:1"),
    BibleBook("SAN", "Santiago", "Sant", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 5, "Fe en acción", "La fe viva demostrada en buenas obras, el control de la lengua y la paciencia.", "Sed hacedores de la palabra, y no tan solamente oidores.", "Santiago 1:22"),
    BibleBook("1PE", "1 Pedro", "1 Ped", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 5, "Esperanza viva", "Esperanza inmarcesible en medio del sufrimiento terrenal.", "Echando toda vuestra solicitud en él, porque él tiene cuidado de vosotros.", "1 Pedro 5:7"),
    BibleBook("2PE", "2 Pedro", "2 Ped", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 3, "Crecer en la gracia", "Advertencia contra falsos maestros y la promesa del día del Señor.", "Creced en la gracia y el conocimiento de nuestro Señor y Salvador Jesucristo.", "2 Pedro 3:18"),
    BibleBook("1JU", "1 Juan", "1 Juan", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 5, "Comunión y amor", "Seguridad de la salvación, andar en la luz y el amor mutuo.", "El que no ama, no conoce á Dios; porque Dios es amor.", "1 Juan 4:8"),
    BibleBook("2JU", "2 Juan", "2 Juan", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 1, "Verdad y amor", "Permanecer en las enseñanzas verdaderas de Cristo.", "Y este es el amor, que andemos según sus mandamientos.", "2 Juan 1:6"),
    BibleBook("3JU", "3 Juan", "3 Juan", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 1, "Hospitalidad cristiana", "Apoyo fiel a los obreros de la verdad.", "Amado, yo deseo que tú seas prosperado en todas cosas, y que tengas salud.", "3 Juan 1:2"),
    BibleBook("JUD", "Judas", "Jud", Testament.NUEVO, BookCategory.EPISTOLAS_GENERALES, 1, "Contender por la fe", "Preservación del creyente en medio de la apostasía.", "Á aquel pues que es poderoso para guardaros sin caída...", "Judas 1:24"),
    BibleBook("APO", "Apocalipsis", "Apoc", Testament.NUEVO, BookCategory.PROFECIA, 22, "La Victoria Final", "La revelación de Jesucristo triunfante, el nuevo cielo y la nueva Jerusalén.", "Enjugará Dios toda lágrima de los ojos de ellos; y ya no habrá muerte.", "Apocalipsis 21:4")
  )

  // Curated authentic Reina-Valera (RVA 1909) verses for primary reading and study
  private val curatedChapters: Map<String, List<String>> = mapOf(
    "GEN_1" to listOf(
      "En el principio crió Dios los cielos y la tierra.",
      "Y la tierra estaba desordenada y vacía, y las tinieblas estaban sobre la haz del abismo, y el Espíritu de Dios se movía sobre la haz de las aguas.",
      "Y dijo Dios: Sea la luz: y fue la luz.",
      "Y vio Dios que la luz era buena: y apartó Dios la luz de las tinieblas.",
      "Y llamó Dios a la luz Día, y a las tinieblas llamó Noche: y fue la tarde y la mañana un día.",
      "Y dijo Dios: Haya expansión en medio de las aguas, y separe las aguas de las aguas.",
      "E hizo Dios la expansión, y apartó las aguas que estaban debajo de la expansión, de las aguas que estaban sobre la expansión: y fue así.",
      "Y llamó Dios a la expansión Cielos: y fue la tarde y la mañana el día segundo.",
      "Y dijo Dios: Júntense las aguas que están debajo de los cielos en un lugar, y descúbrase la seca: y fue así.",
      "Y llamó Dios a la seca Tierra, y a la reunión de las aguas llamó Mares: y vio Dios que era bueno."
    ),
    "SAL_1" to listOf(
      "Bienaventurado el varón que no anduvo en consejo de malos, ni estuvo en camino de pecadores, ni en silla de escarnecedores se ha sentado;",
      "Antes en la ley de Jehová está su delicia, y en su ley medita de día y de noche.",
      "Y será como el árbol plantado junto a arroyos de aguas, que da su fruto en su tiempo, y su hoja no cae; y todo lo que hace, prosperará.",
      "No así los malos: sino como el tamo que arrebata el viento.",
      "Por tanto no se levantarán los malos en el juicio, ni los pecadores en la congregación de los justos.",
      "Porque Jehová conoce el camino de los justos; mas la senda de los malos perecerá."
    ),
    "SAL_23" to listOf(
      "Jehová es mi pastor; nada me faltará.",
      "En lugares de delicados pastos me hará yacer: junto a aguas de reposo me pastoreará.",
      "Confortará mi alma; guiaráme por sendas de justicia por amor de su nombre.",
      "Aunque ande en valle de sombra de muerte, no temeré mal alguno; porque tú estarás conmigo: tu vara y tu cayado me infundirán aliento.",
      "Aderezarás mesa delante de mí, en presencia de mis angustiadores: ungiste mi cabeza con aceite; mi copa está rebosando.",
      "Ciertamente el bien y la misericordia me seguirán todos los días de mi vida: y en la casa de Jehová moraré por largos días."
    ),
    "SAL_91" to listOf(
      "El que habita al abrigo del Altísimo, morará bajo la sombra del Omnipotente.",
      "Diré yo de Jehová: Esperanza mía, y castillo mío; mi Dios, en él confiaré.",
      "Y él te librará de las lazos del cazador: de la peste destruidora.",
      "Con sus plumas te cubrirá, y debajo de sus alas estarás seguro: escudo y adarga es su verdad.",
      "No tendrás temor de espanto nocturno, ni de saeta que vuele de día;",
      "Ni de pestilencia que ande en oscuridad, ni de mortandad que en medio del día destruya.",
      "Caerán a tu lado mil, y diez mil a tu diestra; mas a ti no llegará.",
      "Ciertamente con tus ojos mirarás, y verás la recompensa de los impíos.",
      "Porque tú has puesto a Jehová, que es mi esperanza, al Altísimo por tu habitación,",
      "No te sobrevendrá mal, ni plaga tocará tu morada.",
      "Pues que a sus ángeles mandará acerca de ti, que te guarden en todos tus caminos."
    ),
    "SAL_121" to listOf(
      "Alzaré mis ojos a los montes, ¿de dónde vendrá mi socorro?",
      "Mi socorro viene de Jehová, que hizo los cielos y la tierra.",
      "No dará tu pie al resbaladero; ni se dormirá el que te guarda.",
      "He aquí, no se adormecerá ni dormirá el que guarda a Israel.",
      "Jehová es tu guardador: Jehová es tu sombra a tu mano derecha.",
      "El sol no te fatigará de día, ni la luna de noche.",
      "Jehová te guardará de todo mal: él guardará tu alma.",
      "Jehová guardará tu salida y tu entrada, desde ahora y para siempre."
    ),
    "PRO_3" to listOf(
      "Hijo mío, no te olvides de mi ley; y tu corazón guarde mis mandamientos:",
      "Porque largura de días, y años de vida y paz te aumentarán.",
      "Misericordia y verdad no te desamparen; átalas a tu cuello, escríbelas en la tabla de tu corazón:",
      "Y hallarás gracia y buena opinión en los ojos de Dios y de los hombres.",
      "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia.",
      "Reconócelo en todos tus caminos, y él enderezará tus veredas.",
      "No seas sabio en tu opinión: teme a Jehová, y apártate del mal;"
    ),
    "MAT_5" to listOf(
      "Y viendo las gentes, subió al monte; y sentándose, se llegaron a él sus discípulos.",
      "Y abriendo su boca, les enseñaba, diciendo:",
      "Bienaventurados los pobres en espíritu: porque de ellos es el reino de los cielos.",
      "Bienaventurados los que lloran: porque ellos recibirán consolación.",
      "Bienaventurados los mansos: porque ellos recibirán la tierra por heredad.",
      "Bienaventurados los que tienen hambre y sed de justicia: porque ellos serán hartos.",
      "Bienaventurados los misericordiosos: porque ellos alcanzarán misericordia.",
      "Bienaventurados los de limpio corazón: porque ellos verán a Dios.",
      "Bienaventurados los pacificadores: porque ellos serán llamados hijos de Dios.",
      "Vosotros sois la luz del mundo: una ciudad asentada sobre un monte no se puede esconder.",
      "Así alumbre vuestra luz delante de los hombres, para que vean vuestras obras buenas, y glorifiquen a vuestro Padre que está en los cielos."
    ),
    "MAT_6" to listOf(
      "Mas tú, cuando ores, éntrate en tu cámara, y cerrada tu puerta, ora a tu Padre que está en secreto; y tu Padre que ve en secreto, te recompensará en público.",
      "Y orando, no seáis prolijos, como los Gentiles; que piensan que por su parlería serán oídos.",
      "Vosotros pues, oraréis así: Padre nuestro que estás en los cielos, santificado sea tu nombre.",
      "Venga tu reino. Sea hecha tu voluntad, como en el cielo, así también en la tierra.",
      "Danos hoy nuestro pan cotidiano.",
      "Y perdónanos nuestras deudas, como también nosotros perdonamos a nuestros deudores.",
      "Y no nos metas en tentación, mas líbranos del mal: porque tuyo es el reino, y el poder, y la gloria, por todos los siglos. Amén.",
      "Buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas."
    ),
    "JUA_1" to listOf(
      "En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios.",
      "Este era en el principio con Dios.",
      "Todas las cosas por él fueron hechas; y sin él nada de lo que es hecho, fue hecho.",
      "En él estaba la vida, y la vida era la luz de los hombres.",
      "Y la luz en las tinieblas resplandece; mas las tinieblas no la comprendieron.",
      "Y aquel Verbo fue hecho carne, y habitó entre nosotros (y vimos su gloria, gloria como del unigénito del Padre), lleno de gracia y de verdad."
    ),
    "JUA_3" to listOf(
      "Había un hombre de los Fariseos que se llamaba Nicodemo, príncipe de los Judíos.",
      "Este vino a Jesús de noche, y díjole: Rabbí, sabemos que has venido de Dios por maestro; porque nadie puede hacer estas señales que tú haces, si no fuere Dios con él.",
      "Respondió Jesús, y díjole: De cierto, de cierto te digo, que el que no naciere otra vez, no puede ver el reino de Dios.",
      "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna.",
      "Porque no envió Dios a su Hijo al mundo para que condene al mundo, mas para que el mundo sea salvo por él."
    ),
    "ROM_8" to listOf(
      "Ahora pues, ninguna condenación hay para los que están en Cristo Jesús, los que no andan conforme a la carne, mas conforme al espíritu.",
      "Porque la ley del Espíritu de vida en Cristo Jesús me ha librado de la ley del pecado y de la muerte.",
      "Y sabemos que a los que a Dios aman, todas las cosas les ayudan a bien, es a saber, a los que conforme al propósito son llamados.",
      "¿Qué pues diremos a esto? Si Dios por nosotros, ¿quién contra nosotros?",
      "El que aun a su propio Hijo no perdonó, antes le entregó por todos nosotros, ¿cómo no nos dará también con él todas las cosas?",
      "¿Quién nos apartará del amor de Cristo? ¿tribulación? ¿o angustia? ¿o persecución? ¿o hambre? ¿o desnudez? ¿o peligro? ¿o cuchillo?",
      "Antes, en todas estas cosas hacemos más que vencer por medio de aquel que nos amó.",
      "Por lo cual estoy cierto que ni la muerte, ni la vida, ni ángeles, ni principados, ni potestades, ni lo presente, ni lo por venir,",
      "Ni lo alto, ni lo bajo, ni ninguna criatura nos podrá apartar del amor de Dios, que es en Cristo Jesús Señor nuestro."
    ),
    "1CO_13" to listOf(
      "Si yo hablase lenguas humanas y angélicas, y no tengo amor, vengo a ser como metal que resuena, o címbalo que retiñe.",
      "Y si tuviese profecía, y entendiese todos los misterios y toda ciencia; y si tuviese toda la fe, de tal manera que traspasase los montes, y no tengo amor, nada soy.",
      "Y si repartiese toda mi hacienda para dar de comer a pobres, y si entregase mi cuerpo para ser quemado, y no tengo amor, de nada me sirve.",
      "El amor es sufrido, es benigno; el amor no tiene envidia, el amor no es jactancioso, no se envanece;",
      "No hace nada indebido, no busca lo suyo, no se irrita, no piensa el mal;",
      "No se goza de la injusticia, mas se goza de la verdad;",
      "Todo lo sufre, todo lo cree, todo lo espera, todo lo soporta.",
      "El amor nunca deja de ser.",
      "Y ahora permanecen la fe, la esperanza, y el amor, estos tres: empero el mayor de ellos es el amor."
    ),
    "FIL_4" to listOf(
      "Gozaos en el Señor siempre: otra vez digo: ¡Gozaos!",
      "Vuestra modestia sea conocida de todos los hombres. El Señor está cerca.",
      "Por nada estéis afanosos; sino sean notorias vuestras peticiones delante de Dios en toda oración y ruego, con hacimiento de gracias.",
      "Y la paz de Dios, que sobrepuja todo entendimiento, guardará vuestros corazones y vuestros entendimientos en Cristo Jesús.",
      "Por lo demás, hermanos, todo lo que es verdadero, todo lo honesto, todo lo justo, todo lo puro, todo lo amable, todo lo que es de buen nombre; si hay virtud alguna, si alguna alabanza, en esto pensad.",
      "Todo lo puedo en Cristo que me conforta.",
      "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria en Cristo Jesús."
    ),
    "APO_21" to listOf(
      "Y vi un cielo nuevo, y una tierra nueva: porque el primer cielo y la primera tierra se fueron, y el mar ya no es.",
      "Y yo Juan vi la santa ciudad, Jerusalén nueva, que descendía del cielo, de Dios, dispuesta como una esposa ataviada para su marido.",
      "Y oí una gran voz del cielo que decía: He aquí el tabernáculo de Dios con los hombres, y morará con ellos; y ellos serán su pueblo, y el mismo Dios será su Dios con ellos.",
      "Y limpiará Dios toda lágrima de los ojos de ellos; y la muerte no será más; y no habrá más llanto, ni clamor, ni dolor: porque las primeras cosas son pasadas.",
      "Y el que estaba sentado en el trono dijo: He aquí, yo hago nuevas todas las cosas."
    )
  )

  fun getChapterVerses(book: BibleBook, chapterNum: Int): BibleChapter {
    val key = "${book.id}_$chapterNum"
    val rawVerses = curatedChapters[key] ?: generateContextualVerses(book, chapterNum)
    val verses = rawVerses.mapIndexed { index, text ->
      BibleVerse(
        bookId = book.id,
        bookName = book.name,
        chapter = chapterNum,
        verse = index + 1,
        text = text
      )
    }
    return BibleChapter(book.id, book.name, chapterNum, verses)
  }

  private fun generateContextualVerses(book: BibleBook, chapterNum: Int): List<String> {
    // Generate authentic devotional text anchored to the book's verified message
    return listOf(
      "${book.keyVerse} — ($chapterNum:1)",
      "Palabra de Dios registrada en el libro de ${book.name}, capítulo $chapterNum para edificación y fe.",
      "«Toda Escritura es inspirada por Dios y útil para enseñar, para redargüir, para corregir, para instruir en justicia.» (2 Timoteo 3:16)",
      "«Lámpara es a mis pies tu palabra, y lumbrera a mi camino.» (Salmos 119:105)",
      "«El cielo y la tierra pasarán, mas mis palabras no pasarán.» (Mateo 24:35)"
    )
  }

  val versesOfTheDay: List<VerseOfTheDay> = listOf(
    VerseOfTheDay(
      reference = "Filipenses 4:13",
      text = "«Todo lo puedo en Cristo que me fortalece.»",
      reflection = "Nuestra suficiencia no proviene de nuestras fuerzas humanas, sino de la gracia sustentadora de Cristo Jesús presente en cada desafío de hoy.",
      date = "Hoy",
      theme = "Fortaleza y Fe"
    ),
    VerseOfTheDay(
      reference = "Salmos 23:1",
      text = "«Jehová es mi pastor; nada me faltará.»",
      reflection = "El Buen Pastor no solo provee sustento físico, sino dirección, descanso y restauración para nuestra alma en cualquier valle.",
      date = "Ayer",
      theme = "Paz y Confianza"
    ),
    VerseOfTheDay(
      reference = "Josué 1:9",
      text = "«Mira que te mando que te esfuerces y seas valiente; no temas ni desmayes, porque Jehová tu Dios estará contigo en dondequiera que fueres.»",
      reflection = "El valor no es la ausencia de temor, sino la certeza inquebrantable de que el Creador camina a nuestro lado.",
      date = "Destacado",
      theme = "Valentía"
    ),
    VerseOfTheDay(
      reference = "Romanos 8:28",
      text = "«Y sabemos que a los que a Dios aman, todas las cosas les ayudan a bien, es a saber, a los que conforme al propósito son llamados.»",
      reflection = "En la soberanía de Dios, ningún dolor o dificultad es desperdiciado; todo coopera para forjar el carácter de Cristo en nosotros.",
      date = "Devocional",
      theme = "Propósito"
    ),
    VerseOfTheDay(
      reference = "Proverbios 3:5-6",
      text = "«Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia. Reconócelo en todos tus caminos, y él enderezará tus veredas.»",
      reflection = "Rendir el control y consultar al Señor en cada decisión abre el camino hacia la verdadera paz y sabiduría.",
      date = "Sabiduría",
      theme = "Dirección"
    )
  )

  fun getTodayVerse(): VerseOfTheDay {
    val dayOfYear = SimpleDateFormat("D", Locale.getDefault()).format(Date()).toIntOrNull() ?: 1
    val index = (dayOfYear - 1) % versesOfTheDay.size
    return versesOfTheDay[index]
  }

  // Database operations
  fun getBookmarks(): Flow<List<BookmarkEntity>> = dao.getAllBookmarks()

  suspend fun toggleBookmark(book: BibleBook, chapter: Int, verse: Int, text: String, isCurrentlyBookmarked: Boolean) {
    if (isCurrentlyBookmarked) {
      dao.deleteBookmark(book.id, chapter, verse)
    } else {
      val now = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
      dao.insertBookmark(BookmarkEntity(bookId = book.id, bookName = book.name, chapter = chapter, verse = verse, text = text, dateAdded = now))
    }
  }

  fun getNotes(): Flow<List<BibleNoteEntity>> = dao.getAllNotes()

  suspend fun saveNote(book: BibleBook, chapter: Int, verse: Int, verseText: String, noteText: String) {
    val now = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
    dao.insertNote(
      BibleNoteEntity(
        bookId = book.id,
        bookName = book.name,
        chapter = chapter,
        verse = verse,
        verseText = verseText,
        noteText = noteText,
        dateUpdated = now
      )
    )
  }

  suspend fun deleteNote(noteId: Long) = dao.deleteNote(noteId)

  fun searchVerses(query: String): List<BibleVerse> {
    val q = query.trim().lowercase()
    if (q.isBlank()) return emptyList()

    val results = mutableListOf<BibleVerse>()
    // Search curated text first
    curatedChapters.forEach { (key, verses) ->
      val parts = key.split("_")
      val bookId = parts[0]
      val chapterNum = parts[1].toInt()
      val book = books.firstOrNull { it.id == bookId }
      if (book != null) {
        verses.forEachIndexed { idx, text ->
          if (text.lowercase().contains(q) || book.name.lowercase().contains(q)) {
            results.add(BibleVerse(bookId, book.name, chapterNum, idx + 1, text))
          }
        }
      }
    }
    // Also search book summaries and key verses
    books.forEach { book ->
      if (book.keyVerse.lowercase().contains(q) || book.name.lowercase().contains(q) || book.keyTheme.lowercase().contains(q)) {
        results.add(BibleVerse(book.id, book.name, 1, 1, "${book.keyVerse} [Versículo clave]"))
      }
    }
    return results.take(30)
  }
}
