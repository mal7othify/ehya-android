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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eillia.ehya.viewmodels.AppViewModel
import com.eillia.ehya.viewmodels.SwipeResult

@ExperimentalAnimationApi
@Composable
fun PlayScreen(appViewModel: AppViewModel) {
  val sunan = appViewModel.playSunan
  val showButton by appViewModel.showButton.observeAsState()

  Content(
    sunan = sunan,
    trySunnah = { appViewModel.trySunnah(it) },
    passSunnah = { appViewModel.passSunnah(it) },
    onSwipe = { swipe: SwipeResult, sunnah -> appViewModel.onSwipe(swipe, sunnah) },
    playAgain = { appViewModel.playAgain() },
    showButton = showButton!!
  )
  if (!showButton!!) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(color = MaterialTheme.colors.secondary)
    }
  }
}
