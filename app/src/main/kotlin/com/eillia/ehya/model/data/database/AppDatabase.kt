/*
 * Copyright 2024 Maryam Alhuthayfi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eillia.ehya.model.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.eillia.ehya.helpers.DATABASE_NAME
import com.eillia.ehya.helpers.KEY_FILENAME
import com.eillia.ehya.helpers.SUNAN_DATA_FILENAME
import com.eillia.ehya.model.data.dao.InteractionDao
import com.eillia.ehya.model.data.dao.SunnahDao
import com.eillia.ehya.model.data.entity.Category
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.workers.SeedDatabaseWorker

@Database(
  entities = [Sunnah::class, Category::class, Interaction::class],
  version = 4,
  exportSchema = true,
  autoMigrations = [
    AutoMigration(from = 3, to = 4)
  ]
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun sunnahDao(): SunnahDao

  abstract fun interactionDao(): InteractionDao

  companion object {
    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase =
      instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }

    private fun buildDatabase(context: Context): AppDatabase =
      Room
        .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addCallback(
          object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
              super.onCreate(db)
              val request =
                OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                  .setInputData(workDataOf(KEY_FILENAME to SUNAN_DATA_FILENAME))
                  .build()
              WorkManager.getInstance(context).enqueue(request)
            }
          }
        ).fallbackToDestructiveMigration()
        .build()
  }
}
