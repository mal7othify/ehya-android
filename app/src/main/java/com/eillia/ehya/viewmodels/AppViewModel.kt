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
package com.eillia.ehya.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.model.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AppViewModel @Inject constructor(
  private val appRepository: RepositoryImpl
) : ViewModel() {
  private var _sunan = appRepository.getAllSunan()
  var playSunan = mutableStateListOf<Sunnah>()
    private set

  var sunan = mutableStateListOf<Sunnah>()
    private set

  private val _showButton = MutableLiveData<Boolean>(false)
  val showButton: LiveData<Boolean> = _showButton

  init {
    Timber.d("init AppViewModel")
    showButton()
    viewModelScope.launch {
      _sunan.collect {
        it.forEach { sunnah ->
          Timber.d("list from db $sunnah")
          playSunan.add(sunnah)
          sunan.add(sunnah)
        }
      }
    }
    shuffleSunan()
  }

  private fun shuffleSunan() = viewModelScope.launch {
    playSunan.shuffle()
  }

  fun trySunnah(sunnah: Sunnah) = viewModelScope.launch {
    val interaction = Interaction(sunnah.id, isTried = true, isPassed = false)
    appRepository.insertInteraction(interaction)
    playSunan.remove(sunnah)
    Timber.d("Sunnah's tries updated in database $interaction ")
  }

  fun passSunnah(sunnah: Sunnah) = viewModelScope.launch {
    val interaction = Interaction(sunnah.id, isTried = false, isPassed = true)
    appRepository.insertInteraction(interaction)
    shuffleSunan()
    Timber.d("Sunnah's passes updated in database $interaction ")
  }

  fun onSwipe(swipeResult: SwipeResult, sunnah: Sunnah) = viewModelScope.launch {
    if ((swipeResult == SwipeResult.PASS) && (appRepository.isPassed(sunnah.id) == null)) {
      passSunnah(sunnah)
      Timber.d("swiped pass $swipeResult")
    } else if ((swipeResult == SwipeResult.TRY) && (appRepository.isTried(sunnah.id) == null)) {
      trySunnah(sunnah)
      Timber.d("swiped try $swipeResult")
    }
  }

  fun playAgain() {
    viewModelScope.launch {
      playSunan.clear()
      Timber.d("All sunan are deleted $playSunan")
      _sunan.collect {
        it.forEach { sunnah ->
          playSunan.add(sunnah)
        }
      }
      Timber.d("Get all sunan $playSunan")
    }
  }

  private fun showButton() = viewModelScope.launch {
    delay(1000)
    _showButton.value = true
  }
}

enum class SwipeResult { TRY, PASS }
