package com.example.data.model

enum class Testament {
  ANTIGUO,
  NUEVO
}

enum class BookCategory {
  PENTATEUCO,
  HISTORICOS,
  POETICOS,
  PROFETAS_MAYORES,
  PROFETAS_MENORES,
  EVANGELIOS,
  HISTORIA_IGLESIA,
  EPISTOLAS_PAULINAS,
  EPISTOLAS_GENERALES,
  PROFECIA
}

data class BibleBook(
  val id: String,
  val name: String,
  val abbreviation: String,
  val testament: Testament,
  val category: BookCategory,
  val totalChapters: Int,
  val keyTheme: String,
  val summary: String,
  val keyVerse: String,
  val keyVerseRef: String
)

data class BibleVerse(
  val bookId: String,
  val bookName: String,
  val chapter: Int,
  val verse: Int,
  val text: String,
  val isFavorite: Boolean = false,
  val hasNote: Boolean = false,
  val noteText: String = ""
) {
  val reference: String get() = "$bookName $chapter:$verse"
}

data class BibleChapter(
  val bookId: String,
  val bookName: String,
  val chapter: Int,
  val verses: List<BibleVerse>
)

data class VerseOfTheDay(
  val reference: String,
  val text: String,
  val reflection: String,
  val date: String,
  val theme: String
)
