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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ehya.R
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.ui.components.DraggableCard
import com.eillia.ehya.viewmodels.SwipeResult
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalAnimationApi
@Composable
fun Content(
  sunan: List<Sunnah>,
  trySunnah: (Sunnah) -> Unit,
  passSunnah: (Sunnah) -> Unit,
  onSwipe: (SwipeResult, Sunnah) -> Unit,
  playAgain: () -> Unit,
  showButton: Boolean
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    if (showButton) {
      Button(
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        onClick = { playAgain() }
      ) {
        Text(text = stringResource(id = R.string.playagain))
      }
    }

    sunan.forEachIndexed { index, sunnah ->
      DraggableCard(
        item = sunnah,
        modifier = Modifier
          .padding(
            start = 16.dp,
            top = 16.dp + (index).dp,
            end = 16.dp
          )
          .align(Alignment.BottomCenter)
          .fillMaxSize(),
        onSwiped = { swipeResult: SwipeResult, sunnah ->
          if (sunan.isNotEmpty()) {
            onSwipe(swipeResult, sunnah)
          }
        }
      ) {
        CardContent(
          sunnah,
          trySunnah = { trySunnah(it) },
          passSunnah = { passSunnah(it) },
        )
      }
    }
  }
}
