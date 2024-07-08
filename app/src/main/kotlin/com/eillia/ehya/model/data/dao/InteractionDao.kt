/*
 * Copyright 2022
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

@Dao
interface InteractionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertInteraction(interaction: Interaction)

  @Query("UPDATE interactionTable SET isTried=:isTried, tries=:tries WHERE sunnahId = :id")
  suspend fun updateTries(isTried: Boolean, tries: Int, id: Int)

  @Query("UPDATE interactionTable SET isPassed=:isPassed, passes=:passes WHERE sunnahId = :id")
  suspend fun updatePasses(isPassed: Boolean, passes: Int, id: Int)

  @Query("UPDATE interactionTable SET isFavorite=:isFavorite WHERE sunnahId = :id")
  suspend fun updateFavorite(isFavorite: Boolean, id: Int)

  @Query("SELECT isTried FROM interactionTable WHERE sunnahId = :id")
  suspend fun isTried(id: Int): Boolean

  @Query("SELECT isPassed FROM interactionTable WHERE sunnahId = :id")
  suspend fun isPassed(id: Int): Boolean
}
