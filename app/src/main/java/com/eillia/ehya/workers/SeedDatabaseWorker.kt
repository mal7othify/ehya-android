/*
 * Copyright 2022 The Android Open Source Project
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
package com.eillia.ehya.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eillia.ehya.helpers.KEY_FILENAME
import com.eillia.ehya.model.data.database.AppDatabase
import com.eillia.ehya.model.data.entity.Sunnah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

// TODO: Use Hilt
class SeedDatabaseWorker(
  context: Context,
  workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

  override suspend fun doWork(): Result = coroutineScope {
    withContext(Dispatchers.IO) {
      try {
        val filename = inputData.getString(KEY_FILENAME)
        if (filename != null) {
          applicationContext.assets.open(filename).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
              val sunnahType = object : TypeToken<List<Sunnah>>() {}.type
              val sunan: List<Sunnah> =
                Gson().fromJson<List<Sunnah>?>(jsonReader, sunnahType)
              AppDatabase.getInstance(applicationContext).sunnahDao()
                .insertSunan(sunan)
              Timber.d("Seeding database is successful")
              Result.success()
            }
          }
        } else {
          Timber.e("Error seeding database - no valid filename")
          Result.failure()
        }
      } catch (e: Exception) {
        Timber.e(e, "Error seeding database")
        Result.failure()
      }
    }
  }
}
