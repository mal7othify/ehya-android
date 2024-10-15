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
package com.eillia.ehya.model.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.data.entity.SunnahWithCategory
import com.eillia.ehya.model.data.item.SwipeResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SunnahDao {
  // Insert a list of Sunan, replace any conflicts
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSunan(sunan: List<Sunnah>)

  // Insert a single Sunnah
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSingleSunnah(sunnah: Sunnah)

  // Fetch a single Sunnah with its associated Category by ID
  @Transaction
  @Query("SELECT * FROM Sunan WHERE id = :id") // Updated table name to "Sunan"
  suspend fun getSunnahWithCategory(id: Int): SunnahWithCategory

  // Get all Sunan with Flow to reactively update data
  @Query("SELECT * FROM Sunan") // Updated table name to "Sunan"
  fun getAllSunan(): Flow<List<Sunnah>>

  // Fetch all Sunan with their associated Category (returns a list of SunnahWithCategory)
  @Transaction
  @Query("SELECT * FROM Sunan") // Updated table name to "Sunan"
  fun getAllSunanWithCategory(): Flow<List<SunnahWithCategory>>

  // Get the count of all Sunan
  @Query("SELECT COUNT(id) FROM Sunan") // Updated table name to "Sunan"
  suspend fun getSunanCount(): Int

  // Get a specific Sunnah by its ID
  @Query("SELECT * FROM Sunan WHERE id = :id") // Updated table name to "Sunan"
  suspend fun getSunnah(id: Int): Sunnah?

  // Update a specific Sunnah
  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateSunnah(sunnah: Sunnah)

  // Get all Sunan with a specific swipe result
  @Query("SELECT * FROM Sunan WHERE swipeResult = :swipeResult") // Updated table name to "Sunan"
  suspend fun getAllSwipedSunan(swipeResult: SwipeResult): List<Sunnah>

  // Get the count of Sunan with a specific swipe result
  @Query("SELECT COUNT(id) FROM Sunan WHERE swipeResult = :swipeResult") // Updated table name to "Sunan"
  suspend fun getAllSwipedSunanCount(swipeResult: SwipeResult): Int

  // Get all Sunan by categoryId (find all Sunan in a specific category)
  @Query("SELECT * FROM Sunan WHERE category_id = :categoryId") // Updated foreign key column name to "category_id"
  fun getSunanByCategory(categoryId: Int): Flow<List<Sunnah>>

  // Delete a specific Sunnah by ID
  @Query("DELETE FROM Sunan WHERE id = :id") // Updated table name to "Sunan"
  suspend fun deleteSunnah(id: Int)

  // Delete all Sunan
  @Query("DELETE FROM Sunan") // Updated table name to "Sunan"
  suspend fun deleteAllSunan()
}
