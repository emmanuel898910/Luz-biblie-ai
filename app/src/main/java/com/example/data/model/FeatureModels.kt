package com.example.data.model

// --- Quiz Models ---
enum class QuizCategory(val title: String, val iconName: String) {
  PERSONAJES("Personajes", "person"),
  HISTORIAS("Historias", "auto_stories"),
  SABIDURIA("Sabiduría", "lightbulb"),
  EVANGELIOS("Evangelios", "menu_book")
}

enum class QuizDifficulty(val title: String, val points: Int) {
  FACIL("Fácil", 15),
  MEDIO("Medio", 30),
  AVANZADO("Avanzado", 50)
}

data class QuizQuestion(
  val id: Int,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String,
  val biblicalReference: String,
  val category: QuizCategory,
  val difficulty: QuizDifficulty
)

// --- Study Models ---
data class StudyTopic(
  val id: String,
  val title: String,
  val description: String,
  val iconEmoji: String,
  val verses: List<Pair<String, String>>, // Pair(reference, text)
  val biblicalContext: String,
  val theologicalExplanation: String,
  val reflectionQuestions: List<String>,
  val practicalApplication: String,
  val closingPrayer: String
)

// --- Prayer Models ---
enum class PrayerCategory(val label: String, val emoji: String) {
  GRATITUD("Gratitud", "🙏"),
  SANIDAD("Sanidad", "🌿"),
  FAMILIA("Familia", "🏡"),
  GUIA("Guía y Sabiduría", "🧭"),
  FORTALEZA("Fortaleza", "🛡️"),
  PAZ("Paz interior", "🕊️")
}

data class PrayerItem(
  val id: Long = 0,
  val title: String,
  val description: String,
  val category: PrayerCategory,
  val isAnswered: Boolean = false,
  val dateCreated: String,
  val answeredNote: String = ""
)

// --- Memorization Models ---
data class MemorizationVerse(
  val id: String,
  val reference: String,
  val fullText: String,
  val theme: String,
  val masteryPercent: Int = 0 // 0 to 100
)

// --- Camino Bíblico Models ---
enum class CaminoStatus {
  COMPLETED,
  IN_PROGRESS,
  LOCKED
}

data class CaminoStep(
  val id: Int,
  val order: Int,
  val title: String,
  val subtitle: String,
  val scriptureFocus: String,
  val summary: String,
  val lessonText: String,
  val keyVerse: String,
  val checkpointQuestion: String,
  val checkpointOptions: List<String>,
  val checkpointCorrectIndex: Int,
  val rewardXp: Int,
  val status: CaminoStatus = CaminoStatus.LOCKED
)

// --- Gamification & Profile Models ---
data class DailyChallenge(
  val id: String,
  val title: String,
  val description: String,
  val xpReward: Int,
  val isCompleted: Boolean = false
)

data class Achievement(
  val id: String,
  val title: String,
  val description: String,
  val iconEmoji: String,
  val requiredCount: Int,
  val currentProgress: Int,
  val isUnlocked: Boolean = false
)

data class UserLevel(
  val levelNumber: Int,
  val title: String,
  val badgeEmoji: String,
  val minXp: Int,
  val maxXp: Int
)

object LevelSystem {
  val levels = listOf(
    UserLevel(1, "Semilla de Fe", "🌱", 0, 100),
    UserLevel(2, "Buscador de Luz", "🕯️", 100, 250),
    UserLevel(3, "Lector Fiel", "📖", 250, 500),
    UserLevel(4, "Discípulo Atento", "🕊️", 500, 900),
    UserLevel(5, "Sembrador de Paz", "🌿", 900, 1500),
    UserLevel(6, "Guerrero de Oración", "🛡️", 1500, 2300),
    UserLevel(7, "Portador de Luz", "✝️", 2300, 3500),
    UserLevel(8, "Maestro de Sabiduría", "👑", 3500, 5000)
  )

  fun getLevelForXp(xp: Int): UserLevel {
    return levels.lastOrNull { xp >= it.minXp } ?: levels.first()
  }

  fun getNextLevel(currentLevel: UserLevel): UserLevel? {
    val index = levels.indexOf(currentLevel)
    return if (index != -1 && index + 1 < levels.size) levels[index + 1] else null
  }
}
