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
import com.eillia.ehya.model.data.entity.Interaction
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
  // Insert or replace an interaction record
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertInteraction(interaction: Interaction)

  // Update tries for a specific sunnah interaction
  @Query("UPDATE Interaction SET isTried=:isTried, tries=:tries WHERE sunnahId = :id")
  suspend fun updateTries(
    isTried: Boolean,
    tries: Int,
    id: Int
  )

  // Update passes for a specific sunnah interaction
  @Query("UPDATE Interaction SET isPassed=:isPassed, passes=:passes WHERE sunnahId = :id")
  suspend fun updatePasses(
    isPassed: Boolean,
    passes: Int,
    id: Int
  )

  // Update the favorite status of a sunnah interaction
  @Query("UPDATE Interaction SET isFavorite=:isFavorite WHERE sunnahId = :id")
  suspend fun updateFavorite(
    isFavorite: Boolean,
    id: Int
  )

  // Check if a specific sunnah is tried
  @Query("SELECT isTried FROM Interaction WHERE sunnahId = :id")
  suspend fun isTried(id: Int): Boolean

  // Check if a specific sunnah is passed
  @Query("SELECT isPassed FROM Interaction WHERE sunnahId = :id")
  suspend fun isPassed(id: Int): Boolean

  // Get all interactions related to a specific sunnah
  @Query("SELECT * FROM Interaction WHERE sunnahId = :id")
  suspend fun getInteractionsBySunnahId(id: Int): List<Interaction>

  // Get all interactions marked as favorite
  @Query("SELECT * FROM Interaction WHERE isFavorite = 1")
  fun getFavoriteInteractions(): Flow<List<Interaction>>

  // Delete interaction by sunnahId
  @Query("DELETE FROM Interaction WHERE sunnahId = :sunnahId")
  suspend fun deleteInteraction(sunnahId: Int)

  // Delete all interactions
  @Query("DELETE FROM Interaction")
  suspend fun deleteAllInteractions()
}
