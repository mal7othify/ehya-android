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
package com.eillia.ehya.model.repository

import com.eillia.ehya.model.data.dao.InteractionDao
import com.eillia.ehya.model.data.dao.SunnahDao
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.data.item.SwipeResult
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryImpl
  @Inject
  constructor(
    private val sunnahDao: SunnahDao,
    private val interactionDao: InteractionDao
  ) : Repository {
    init {
      Timber.d("Injection RepositoryImpl")
    }

    override suspend fun insertSunan(sunan: List<Sunnah>) =
      withContext(Dispatchers.IO) {
        sunnahDao.insertSunan(sunan)
      }

    override fun getAllSunan(): Flow<List<Sunnah>> = sunnahDao.getAllSunan()

    override suspend fun getSunanCount(): Int = sunnahDao.getSunanCount()

    override suspend fun getSunnah(id: Int): Sunnah? = sunnahDao.getSunnah(id)

    override suspend fun updateSunnah(sunnah: Sunnah) = sunnahDao.updateSunnah(sunnah)

    override suspend fun getAllSwipedSunan(swipeResult: SwipeResult): List<Sunnah> =
      sunnahDao.getAllSwipedSunan(swipeResult)

    override suspend fun getAllSwipedSunanCount(swipeResult: SwipeResult): Int =
      sunnahDao.getAllSwipedSunanCount(swipeResult)

    override suspend fun insertInteraction(interaction: Interaction) {
      interactionDao.insertInteraction(interaction)
    }

    override suspend fun updateTries(
      isTried: Boolean,
      id: Int,
      tries: Int
    ) =
      withContext(Dispatchers.IO) {
        interactionDao.updateTries(isTried, id, tries)
      }

    override suspend fun updatePasses(
      isPassed: Boolean,
      id: Int,
      passes: Int
    ) =
      withContext(Dispatchers.IO) {
        interactionDao.updatePasses(isPassed, id, passes)
      }

    override suspend fun updateFavorite(
      isTried: Boolean,
      id: Int
    ) =
      withContext(Dispatchers.IO) {
        interactionDao.updateFavorite(isTried, id)
      }

    override suspend fun isTried(id: Int): Boolean = interactionDao.isTried(id)

    override suspend fun isPassed(id: Int): Boolean = interactionDao.isPassed(id)
  }
