package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    UserStatsEntity::class,
    BookmarkEntity::class,
    BibleNoteEntity::class,
    PrayerEntity::class,
    MemorizedVerseEntity::class,
    CaminoProgressEntity::class,
    CompletedChallengeEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class LuzDatabase : RoomDatabase() {
  abstract fun luzDao(): LuzDao

  companion object {
    @Volatile
    private var INSTANCE: LuzDatabase? = null

    fun getInstance(context: Context): LuzDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          LuzDatabase::class.java,
          "luz_faith_database.db"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
