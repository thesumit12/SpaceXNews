package com.slakra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slakra.data.model.NewsArticleModel

private const val DB_NAME = "spaceX.db"

@Database(
        entities = [NewsArticleModel::class],
        exportSchema = false,
        version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun newsArticleDao(): NewsArticleDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                AppDatabase::class.java, DB_NAME
        ).build()
    }
}