package com.example.data.repository

import com.example.data.local.CaminoProgressEntity
import com.example.data.local.CompletedChallengeEntity
import com.example.data.local.LuzDao
import com.example.data.local.MemorizedVerseEntity
import com.example.data.local.PrayerEntity
import com.example.data.local.UserStatsEntity
import com.example.data.model.Achievement
import com.example.data.model.DailyChallenge
import com.example.data.model.LevelSystem
import com.example.data.model.PrayerCategory
import com.example.data.model.PrayerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserDataRepository(private val dao: LuzDao) {

  private val todayDateString: String
    get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

  val userStatsFlow: Flow<UserStatsEntity> = dao.getUserStatsFlow().map { entity ->
    entity ?: UserStatsEntity(id = 1, xp = 120, streakDays = 3, lastActiveDate = todayDateString)
  }

  suspend fun ensureInitialized() {
    val current = dao.getUserStats()
    if (current == null) {
      dao.insertUserStats(
        UserStatsEntity(
          id = 1,
          xp = 120,
          streakDays = 3,
          lastActiveDate = todayDateString,
          chaptersRead = 2,
          versesMemorized = 1,
          quizzesCompleted = 1,
          prayersAnswered = 1
        )
      )
      // Insert default initial prayer item for instant gratification
      dao.insertPrayer(
        PrayerEntity(
          title = "Paz para mi familia y salud",
          description = "Poniendo en las manos de Dios la semana de trabajo y la bendición de mi hogar.",
          category = PrayerCategory.FAMILIA.name,
          isAnswered = false,
          dateCreated = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        )
      )
      dao.insertPrayer(
        PrayerEntity(
          title = "Agradecimiento por un nuevo día",
          description = "Gracias Señor por el aliento de vida y por renovar tus misericordias esta mañana.",
          category = PrayerCategory.GRATITUD.name,
          isAnswered = true,
          dateCreated = "01/09/2026",
          answeredNote = "Dios escuchó mi gratitud y llenó mi día de gozo."
        )
      )
      // Unlock step 1 of camino
      dao.setCaminoStepCompleted(CaminoProgressEntity(stepId = 1, isCompleted = true, score = 50))
    } else {
      // Check streak
      if (current.lastActiveDate != todayDateString) {
        val updated = current.copy(
          streakDays = current.streakDays + 1,
          lastActiveDate = todayDateString
        )
        dao.insertUserStats(updated)
      }
    }
  }

  suspend fun addXp(amount: Int) {
    val current = dao.getUserStats() ?: UserStatsEntity(id = 1, xp = 0)
    dao.insertUserStats(current.copy(xp = current.xp + amount))
  }

  suspend fun incrementChaptersRead() {
    val current = dao.getUserStats() ?: UserStatsEntity(id = 1)
    dao.insertUserStats(current.copy(chaptersRead = current.chaptersRead + 1, xp = current.xp + 25))
  }

  suspend fun incrementQuizzes() {
    val current = dao.getUserStats() ?: UserStatsEntity(id = 1)
    dao.insertUserStats(current.copy(quizzesCompleted = current.quizzesCompleted + 1))
  }

  suspend fun incrementMemorized() {
    val current = dao.getUserStats() ?: UserStatsEntity(id = 1)
    dao.insertUserStats(current.copy(versesMemorized = current.versesMemorized + 1, xp = current.xp + 40))
  }

  // Daily Challenges
  fun getDailyChallenges(completedIds: List<String>): List<DailyChallenge> {
    val base = listOf(
      DailyChallenge("ch_verse", "Luz del Día", "Medita y reflexiona en el versículo de hoy", 20),
      DailyChallenge("ch_read", "Lector Fiel", "Lee un capítulo completo de la Biblia", 30),
      DailyChallenge("ch_quiz", "Sabiduría Bíblica", "Completa un quiz con al menos 3 preguntas", 25),
      DailyChallenge("ch_prayer", "Altar Personal", "Registra o eleva una oración en tu diario", 20),
      DailyChallenge("ch_memory", "Memoria Santa", "Practica la memorización de un versículo clave", 35)
    )
    return base.map { ch ->
      ch.copy(isCompleted = completedIds.contains(ch.id))
    }
  }

  fun getCompletedChallengeIds(): Flow<List<String>> =
    dao.getCompletedChallengesForDate(todayDateString)

  suspend fun completeChallenge(challengeId: String, xpReward: Int) {
    dao.markChallengeCompleted(CompletedChallengeEntity(challengeId, todayDateString))
    addXp(xpReward)
  }

  // Achievements
  fun calculateAchievements(stats: UserStatsEntity): List<Achievement> {
    return listOf(
      Achievement(
        id = "ach_first_verse",
        title = "Primera Luz 🕯️",
        description = "Lee tu primer capítulo bíblico en la app.",
        iconEmoji = "🕯️",
        requiredCount = 1,
        currentProgress = stats.chaptersRead,
        isUnlocked = stats.chaptersRead >= 1
      ),
      Achievement(
        id = "ach_streak_3",
        title = "Constancia Fiel 🔥",
        description = "Alcanza una racha de 3 días consecutivos.",
        iconEmoji = "🔥",
        requiredCount = 3,
        currentProgress = stats.streakDays,
        isUnlocked = stats.streakDays >= 3
      ),
      Achievement(
        id = "ach_quiz_master",
        title = "Erudito Bíblico 🧠",
        description = "Supera 5 desafíos o cuestionarios bíblicos.",
        iconEmoji = "📜",
        requiredCount = 5,
        currentProgress = stats.quizzesCompleted,
        isUnlocked = stats.quizzesCompleted >= 5
      ),
      Achievement(
        id = "ach_prayer_warrior",
        title = "Guerrero de Oración 🙏",
        description = "Registra y observa respuestas de oración en tu altar.",
        iconEmoji = "🛡️",
        requiredCount = 2,
        currentProgress = stats.prayersAnswered,
        isUnlocked = stats.prayersAnswered >= 1
      ),
      Achievement(
        id = "ach_memory_master",
        title = "Palabra en el Corazón 💎",
        description = "Memoriza 3 versículos bíblicos.",
        iconEmoji = "💎",
        requiredCount = 3,
        currentProgress = stats.versesMemorized,
        isUnlocked = stats.versesMemorized >= 3
      ),
      Achievement(
        id = "ach_level_disciple",
        title = "Discípulo de Luz ✝️",
        description = "Alcanza 500 puntos de experiencia en la app.",
        iconEmoji = "✨",
        requiredCount = 500,
        currentProgress = stats.xp,
        isUnlocked = stats.xp >= 500
      )
    )
  }

  // Prayers
  val prayersFlow: Flow<List<PrayerItem>> = dao.getAllPrayers().map { list ->
    list.map { entity ->
      PrayerItem(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        category = try {
          PrayerCategory.valueOf(entity.category)
        } catch (_: Exception) {
          PrayerCategory.GRATITUD
        },
        isAnswered = entity.isAnswered,
        dateCreated = entity.dateCreated,
        answeredNote = entity.answeredNote
      )
    }
  }

  suspend fun addPrayer(title: String, description: String, category: PrayerCategory) {
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    dao.insertPrayer(
      PrayerEntity(
        title = title,
        description = description,
        category = category.name,
        isAnswered = false,
        dateCreated = date
      )
    )
    addXp(20)
  }

  suspend fun markPrayerAnswered(prayerId: Long, note: String) {
    val current = dao.getAllPrayers() // We can update through DAO update
    // Read stats to update prayersAnswered count
    val stats = dao.getUserStats() ?: UserStatsEntity(id = 1)
    dao.insertUserStats(stats.copy(prayersAnswered = stats.prayersAnswered + 1, xp = stats.xp + 40))
  }

  suspend fun updatePrayer(prayer: PrayerItem) {
    dao.updatePrayer(
      PrayerEntity(
        id = prayer.id,
        title = prayer.title,
        description = prayer.description,
        category = prayer.category.name,
        isAnswered = prayer.isAnswered,
        dateCreated = prayer.dateCreated,
        answeredNote = prayer.answeredNote
      )
    )
  }

  suspend fun deletePrayer(prayerId: Long) {
    dao.deletePrayer(prayerId)
  }

  // Memorization
  fun getMemorizedVerses(): Flow<List<MemorizedVerseEntity>> = dao.getAllMemorizedVerses()

  suspend fun saveMemorizationProgress(verseRef: String, text: String, theme: String, mastery: Int) {
    dao.insertMemorizedVerse(
      MemorizedVerseEntity(
        verseRef = verseRef,
        fullText = text,
        theme = theme,
        masteryPercent = mastery,
        timesReviewed = 1
      )
    )
    if (mastery >= 100) {
      incrementMemorized()
    }
  }

  // Camino
  fun getCaminoProgress(): Flow<List<CaminoProgressEntity>> = dao.getCaminoProgress()

  suspend fun completeCaminoStep(stepId: Int, rewardXp: Int) {
    dao.setCaminoStepCompleted(CaminoProgressEntity(stepId = stepId, isCompleted = true, score = rewardXp))
    addXp(rewardXp)
  }
}
