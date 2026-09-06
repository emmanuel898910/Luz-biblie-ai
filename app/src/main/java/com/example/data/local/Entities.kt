package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStatsEntity(
  @PrimaryKey val id: Int = 1,
  val xp: Int = 50,
  val streakDays: Int = 1,
  val lastActiveDate: String = "",
  val chaptersRead: Int = 0,
  val versesMemorized: Int = 0,
  val quizzesCompleted: Int = 0,
  val prayersAnswered: Int = 0
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val bookId: String,
  val bookName: String,
  val chapter: Int,
  val verse: Int,
  val text: String,
  val dateAdded: String
)

@Entity(tableName = "bible_notes")
data class BibleNoteEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val bookId: String,
  val bookName: String,
  val chapter: Int,
  val verse: Int,
  val verseText: String,
  val noteText: String,
  val dateUpdated: String
)

@Entity(tableName = "prayers")
data class PrayerEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val description: String,
  val category: String,
  val isAnswered: Boolean = false,
  val dateCreated: String,
  val answeredNote: String = ""
)

@Entity(tableName = "memorized_verses")
data class MemorizedVerseEntity(
  @PrimaryKey val verseRef: String,
  val fullText: String,
  val theme: String,
  val masteryPercent: Int,
  val timesReviewed: Int
)

@Entity(tableName = "camino_progress")
data class CaminoProgressEntity(
  @PrimaryKey val stepId: Int,
  val isCompleted: Boolean = false,
  val score: Int = 0
)

@Entity(tableName = "completed_challenges")
data class CompletedChallengeEntity(
  @PrimaryKey val challengeId: String,
  val dateCompleted: String
)
