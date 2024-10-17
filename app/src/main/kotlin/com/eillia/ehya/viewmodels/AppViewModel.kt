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
package com.eillia.ehya.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eillia.ehya.helpers.SUNAN_PER_ROUND
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.SunnahWithCategory
import com.eillia.ehya.model.data.item.SwipeResult
import com.eillia.ehya.model.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel @Inject constructor(
  private val appRepository: RepositoryImpl
) : ViewModel() {
  val sunanFlow = appRepository.getAllSunanWithCategory()

  private val allPlaySunan = mutableListOf<SunnahWithCategory>()
  private val remainingSunan = mutableListOf<SunnahWithCategory>()
  private val currentSunanSegment = mutableListOf<SunnahWithCategory>()
  val currentSunanSegmentFlow = MutableStateFlow(mutableListOf<SunnahWithCategory>())
  val isLoading = MutableStateFlow(false)

  init {
    viewModelScope.launch {
      getAllPlaySunan()
    }
  }

  private fun getAllPlaySunan() = viewModelScope.launch {
    isLoading.emit(true)
    sunanFlow.collect { sunanList ->
      if (sunanList.isNotEmpty() && allPlaySunan.isEmpty()) {
        allPlaySunan.addAll(sunanList)
        remainingSunan.addAll(sunanList) // Keep track of remaining
        getNextSunanSegment()
      } else if (sunanList.isEmpty()) {
        handleEmptyState()
      }
    }
  }

  private fun getNextSunanSegment() = viewModelScope.launch {
    currentSunanSegment.clear()

    if (remainingSunan.isEmpty()) {
      // Refill from the original list if needed
      remainingSunan.addAll(allPlaySunan)
    }

    // Take the next batch of SUNAN_PER_ROUND
    val nextSegment = remainingSunan.take(SUNAN_PER_ROUND)
    currentSunanSegment.addAll(nextSegment)
    remainingSunan.removeAll(nextSegment)

    if (currentSunanSegment.isNotEmpty()) {
      val shuffledSunan = shuffleSunan(currentSunanSegment)
      currentSunanSegmentFlow.emit(shuffledSunan.toMutableList())
      isLoading.emit(false)
    } else {
      handleEmptyState()
    }
  }

  fun onSwipe(swipeResult: SwipeResult, sunnah: SunnahWithCategory) = viewModelScope.launch {
    val isTried = swipeResult == SwipeResult.TRY
    val isPassed = swipeResult == SwipeResult.PASS
    updateInteractions(sunnah, swipeResult, isTried, isPassed)

    currentSunanSegment.remove(sunnah)

    if (currentSunanSegment.isEmpty()) {
      // When the current segment is empty, fetch the next segment
      getNextSunanSegment()
    } else {
      // Shuffle and emit the remaining items in the current segment
      val shuffledSunan = shuffleSunan(currentSunanSegment)
      currentSunanSegmentFlow.emit(shuffledSunan.toMutableList())
    }
  }


  private fun updateInteractions(
    sunnah: SunnahWithCategory,
    swipeResult: SwipeResult,
    isTried: Boolean,
    isPassed: Boolean
  ) = viewModelScope.launch {
    val interaction = Interaction(
      sunnahId = sunnah.sunnah.id,
      isTried = isTried,
      isPassed = isPassed
    )
    appRepository.insertInteraction(interaction)
    appRepository.updateSunnah(sunnah.sunnah.copy(swipeResult = swipeResult))
  }

  private fun handleEmptyState() = viewModelScope.launch {
    isLoading.emit(false)
    currentSunanSegmentFlow.emit(mutableListOf())
  }

  fun playAgain() = viewModelScope.launch {
    // Reset the state for playing again
    allPlaySunan.forEach { sunnah ->
      appRepository.updateSunnah(sunnah.sunnah.copy(swipeResult = SwipeResult.NONE))
    }
    remainingSunan.clear()
    remainingSunan.addAll(allPlaySunan)
    getNextSunanSegment()
  }

  private fun shuffleSunan(sunan: List<SunnahWithCategory>): List<SunnahWithCategory> = sunan.shuffled()

  private fun allTriedSunan() =
    flow {
      emit(
        appRepository
          .getAllSwipedSunan(SwipeResult.TRY)
          .map { it.title }
          .sorted()
      )
    }.flowOn((Dispatchers.IO))

  private fun allPassedSunan() =
    flow {
      emit(
        appRepository
          .getAllSwipedSunan(SwipeResult.PASS)
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
