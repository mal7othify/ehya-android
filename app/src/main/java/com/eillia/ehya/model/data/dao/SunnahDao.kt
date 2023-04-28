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
import androidx.room.Update
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.data.item.SwipeResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SunnahDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSunan(sunan: List<Sunnah>)

  @Query("SELECT * FROM sunnahTable")
  fun getAllSunan(): Flow<List<Sunnah>>

  @Query("SELECT COUNT(id) FROM sunnahTable")
  fun getSunanCount(): Int

  @Query(
    "SELECT * FROM sunnahTable JOIN interactionTable " +
      "WHERE sunnahTable.id = interactionTable.sunnahId " +
      "AND isFavorite = 1"
  )
  fun getFavSunan(): Flow<List<Sunnah>>

  @Query("SELECT * FROM sunnahTable WHERE id = :id")
  fun getSunnah(id: Int): Sunnah?

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateSunnah(sunnah: Sunnah)

  @Query("SELECT * FROM sunnahTable WHERE swipeResult = (:swipeResult)")
  suspend fun getAllSwipedSunan(swipeResult: SwipeResult): List<Sunnah>

  @Query("SELECT COUNT(id) FROM sunnahTable WHERE swipeResult = (:swipeResult)")
  suspend fun getAllSwipedSunanCount(swipeResult: SwipeResult): Int

  @Query("DELETE FROM sunnahTable WHERE id = :id")
  suspend fun deleteSunnah(id: Int)

  @Query("DELETE FROM sunnahTable")
  suspend fun deleteSunan()
}
