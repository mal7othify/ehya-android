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
package com.eillia.ehya.ui.screens.sunan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eillia.ehya.viewmodels.AppViewModel

@Composable
fun SunanScreen(
  contentPadding: PaddingValues,
  appViewModel: AppViewModel = hiltViewModel()
) {
  val sunan by appViewModel.sunanFlow.collectAsStateWithLifecycle(listOf())
  val listState = rememberLazyListState()

  if (sunan.isNotEmpty()) {
    LazyColumn(
      state = listState,
      modifier =
        Modifier
          .fillMaxWidth(),
      contentPadding = contentPadding
    ) {
      items(sunan) { sunnah ->
        SunnahCard(
          sunnah.sunnah.title,
          sunnah.sunnah.quantity ?: "",
          sunnah.sunnah.hadith
        )
      }
    }
  } else {
    Box(
      Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "لا يوجد سنن متاحة", style = MaterialTheme.typography.body1)
    }
  }
}
