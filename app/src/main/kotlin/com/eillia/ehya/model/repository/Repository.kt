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
package com.eillia.ehya.model.repository

import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.data.item.SwipeResult
import kotlinx.coroutines.flow.Flow

interface Repository {
  suspend fun insertSunan(sunan: List<Sunnah>)
  fun getAllSunan(): Flow<List<Sunnah>>
  suspend fun getSunanCount(): Int
  suspend fun getSunnah(id: Int): Sunnah?
  suspend fun updateSunnah(sunnah: Sunnah)
  suspend fun getAllSwipedSunan(swipeResult: SwipeResult): List<Sunnah>
  suspend fun getAllSwipedSunanCount(swipeResult: SwipeResult): Int

  suspend fun insertInteraction(interaction: Interaction)
  suspend fun updateTries(isTried: Boolean, id: Int, tries: Int)
  suspend fun updatePasses(isPassed: Boolean, id: Int, passes: Int)
  suspend fun updateFavorite(isTried: Boolean, id: Int)
  suspend fun isTried(id: Int): Boolean
  suspend fun isPassed(id: Int): Boolean
}
