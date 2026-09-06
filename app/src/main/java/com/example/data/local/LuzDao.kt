package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LuzDao {
  // Stats
  @Query("SELECT * FROM user_stats WHERE id = 1")
  fun getUserStatsFlow(): Flow<UserStatsEntity?>

  @Query("SELECT * FROM user_stats WHERE id = 1")
  suspend fun getUserStats(): UserStatsEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUserStats(stats: UserStatsEntity)

  // Bookmarks
  @Query("SELECT * FROM bookmarks ORDER BY id DESC")
  fun getAllBookmarks(): Flow<List<BookmarkEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE bookId = :bookId AND chapter = :chapter AND verse = :verse)")
  fun isBookmarked(bookId: String, chapter: Int, verse: Int): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertBookmark(bookmark: BookmarkEntity)

  @Query("DELETE FROM bookmarks WHERE bookId = :bookId AND chapter = :chapter AND verse = :verse")
  suspend fun deleteBookmark(bookId: String, chapter: Int, verse: Int)

  // Notes
  @Query("SELECT * FROM bible_notes ORDER BY id DESC")
  fun getAllNotes(): Flow<List<BibleNoteEntity>>

  @Query("SELECT * FROM bible_notes WHERE bookId = :bookId AND chapter = :chapter AND verse = :verse LIMIT 1")
  suspend fun getNoteForVerse(bookId: String, chapter: Int, verse: Int): BibleNoteEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: BibleNoteEntity)

  @Query("DELETE FROM bible_notes WHERE id = :noteId")
  suspend fun deleteNote(noteId: Long)

  // Prayers
  @Query("SELECT * FROM prayers ORDER BY isAnswered ASC, id DESC")
  fun getAllPrayers(): Flow<List<PrayerEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPrayer(prayer: PrayerEntity): Long

  @Update
  suspend fun updatePrayer(prayer: PrayerEntity)

  @Query("DELETE FROM prayers WHERE id = :prayerId")
  suspend fun deletePrayer(prayerId: Long)

  // Memorized Verses
  @Query("SELECT * FROM memorized_verses")
  fun getAllMemorizedVerses(): Flow<List<MemorizedVerseEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMemorizedVerse(verse: MemorizedVerseEntity)

  // Camino Progress
  @Query("SELECT * FROM camino_progress")
  fun getCaminoProgress(): Flow<List<CaminoProgressEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun setCaminoStepCompleted(step: CaminoProgressEntity)

  // Completed Challenges
  @Query("SELECT challengeId FROM completed_challenges WHERE dateCompleted = :date")
  fun getCompletedChallengesForDate(date: String): Flow<List<String>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun markChallengeCompleted(challenge: CompletedChallengeEntity)
}
