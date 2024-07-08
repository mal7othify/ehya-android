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
package com.eillia.ehya.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eillia.ehya.helpers.SUNAN_PER_ROUND
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.data.item.SwipeResult
import com.eillia.ehya.model.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AppViewModel
  @Inject
  constructor(
    private val appRepository: RepositoryImpl
  ) : ViewModel() {
    val sunanFlow = appRepository.getAllSunan()

    private val allPlaySunan = mutableListOf<Sunnah>()
    private val currentSunanSegment = mutableListOf<Sunnah>()
    val currentSunanSegmentFlow = MutableStateFlow(mutableListOf<Sunnah>())
    val isLoading = MutableStateFlow(false)

    init {
      viewModelScope.launch {
        getAllPlaySunan()
      }
    }

    private fun getAllPlaySunan() =
      viewModelScope.launch {
        isLoading.emit(true)
        sunanFlow.collect {
          // TODO: Enhance the Flow collection method below
          if (it.isNotEmpty() && allPlaySunan.isEmpty()) {
            allPlaySunan.addAll(it)
            getCurrentSunanSegment()
          } else if (it.isEmpty() && allPlaySunan.isEmpty()) {
            allPlaySunan.addAll(emptyList())
            handleEmptyState()
          }
        }
      }

    private fun getCurrentSunanSegment() =
      viewModelScope.launch {
        currentSunanSegment.clear()
        val takeLastSegmentOfOriginal = allPlaySunan.takeLast(SUNAN_PER_ROUND)
        currentSunanSegment.addAll(takeLastSegmentOfOriginal)
        Timber.e("Sunan List getCurrentSunanSegment: currentSunanSegment: $currentSunanSegment")
        if (currentSunanSegment.isNotEmpty()) {
          allPlaySunan.removeAll(currentSunanSegment)
          val ids = arrayListOf<Int>()
          allPlaySunan.forEach {
            ids.add(it.id)
          }
          Timber.e("Sunan List getCurrentSunanSegment: allPlaySunan IDs: $ids")
          val shuffledSunan = shuffleSunan(currentSunanSegment)
          isLoading.emit(false)
          currentSunanSegmentFlow.emit(shuffledSunan.toMutableList())
        } else {
          handleEmptyState()
        }
      }

    fun onSwipe(
      swipeResult: SwipeResult,
      sunnah: Sunnah
    ) =
      viewModelScope.launch {
        val isTried = swipeResult == SwipeResult.TRY
        val isPassed = swipeResult == SwipeResult.PASS
        updateInteractions(sunnah, swipeResult, isTried, isPassed)

        currentSunanSegment.remove(sunnah)
        if (currentSunanSegment.size > 0) {
          val shuffledSunan = shuffleSunan(currentSunanSegment)
          currentSunanSegmentFlow.emit(shuffledSunan.toMutableList())
          Timber.e("Swiped $swipeResult, $currentSunanSegment")
        } else {
          Timber.e("Swiped $swipeResult on last Sunnah in the current segment")
          getCurrentSunanSegment()
        }
      }

    private fun updateInteractions(
      sunnah: Sunnah,
      swipeResult: SwipeResult,
      isTried: Boolean,
      isPassed: Boolean
    ) = viewModelScope.launch {
      val interaction =
        Interaction(
          sunnah.id,
          isTried = isTried,
          isPassed = isPassed
        )
      appRepository.insertInteraction(interaction)
      appRepository.updateSunnah(sunnah.copy(swipeResult = swipeResult))
    }

    private fun handleEmptyState() =
      viewModelScope.launch {
        isLoading.emit(false)
        currentSunanSegmentFlow.emit(mutableListOf())
      }

    fun playAgain() =
      viewModelScope.launch {
        allPlaySunan.forEach { sunnah ->
          appRepository.updateSunnah(sunnah.copy(swipeResult = SwipeResult.NONE))
        }
        allPlaySunan.clear()
        getAllPlaySunan()
      }

    private fun shuffleSunan(sunan: List<Sunnah>): List<Sunnah> = sunan.shuffled()

    private fun allTriedSunan() =
      flow {
        emit(
          appRepository.getAllSwipedSunan(SwipeResult.TRY)
            .map { it.title }
            .sorted()
        )
      }.flowOn((Dispatchers.IO))

    private fun allPassedSunan() =
      flow {
        emit(
          appRepository.getAllSwipedSunan(SwipeResult.PASS)
            .map { it.title }
            .sorted()
        )
      }.flowOn((Dispatchers.IO))

    private fun allTriedSunanCount() =
      flow {
        emit(appRepository.getAllSwipedSunan(SwipeResult.TRY))
      }.flowOn((Dispatchers.IO))

    private fun allPassedSunanCount() =
      flow {
        emit(appRepository.getAllSwipedSunan(SwipeResult.PASS))
      }.flowOn((Dispatchers.IO))
  }
