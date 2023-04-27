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
package com.eillia.ehya.ui.screens.play

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.eillia.ehya.model.data.item.SwipeEvent
import com.eillia.ehya.model.data.item.SwipeResult
import com.eillia.ehya.viewmodels.AppViewModel
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun PlayScreen(appViewModel: AppViewModel = hiltViewModel()) {
  var onSwipeEvent by remember { mutableStateOf(SwipeEvent()) }
  val sunan by appViewModel.currentSunanSegmentFlow.collectAsState()
  val isLoading by appViewModel.isLoading.collectAsState()

  LaunchedEffect(key1 = onSwipeEvent) {
    onSwipeEvent.sunnah?.let {
      appViewModel.onSwipe(onSwipeEvent.swipeResult, it)
      Timber.e("LaunchedEffect -> ${onSwipeEvent.swipeResult}, ${onSwipeEvent.sunnah}")
    }
  }
  Timber.e("Inside PlayScreen -> observing sunan $sunan")

  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    when {
      isLoading -> {
        CircularProgressIndicator(color = MaterialTheme.colors.secondary)
      }

      sunan.isEmpty() -> {
        Text(text = "لا توجد سنن متاحة", color = MaterialTheme.colors.secondary)
      }

      else -> {
        Content(
          sunan = sunan,
          trySunnah = { onSwipeEvent = SwipeEvent(SwipeResult.TRY, it) },
          passSunnah = { onSwipeEvent = SwipeEvent(SwipeResult.PASS, it) },
          onSwipe = { swipe: SwipeResult, sunnah ->
            onSwipeEvent = SwipeEvent(swipe, sunnah)
            Timber.e("Content -> ${onSwipeEvent.swipeResult}, ${onSwipeEvent.sunnah}")
          },
          playAgain = { appViewModel.playAgain() }
        )
      }
    }
  }
}
