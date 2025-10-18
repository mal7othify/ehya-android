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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eillia.ehya.ui.components.SearchOutlinedTextField
import com.eillia.ehya.ui.utils.Dimens
import com.eillia.ehya.viewmodels.AppViewModel

@Composable
fun SunanScreen(
  contentPadding: PaddingValues,
  appViewModel: AppViewModel = hiltViewModel()
) {
  val allSunan by appViewModel.sunanFlow.collectAsStateWithLifecycle(listOf())
  val filteredSunan by appViewModel.filteredSunanFlow.collectAsStateWithLifecycle(listOf())
  val searchQuery by appViewModel.searchQuery.collectAsStateWithLifecycle()
  val listState = rememberLazyListState()

  val showSearchField = listState.firstVisibleItemIndex == 0

  if (allSunan.isEmpty()) {
    Box(
      Modifier
        .fillMaxSize()
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "لا يوجد سنن متاحة",
        style = MaterialTheme.typography.bodyMedium
      )
    }
  } else {
    Box(modifier = Modifier.fillMaxSize()) {
      LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = contentPadding
      ) {
        item {
          // Search Bar - always shown as first item
          AnimatedVisibility(visible = showSearchField) {
            Column(Modifier.fillMaxWidth()) {
              SearchOutlinedTextField(
                searchQuery = searchQuery,
                onValueChange = { appViewModel.updateSearchQuery(it) },
                onCleanSearch = { appViewModel.updateSearchQuery("") }
              )
            }
          }
        }

        // Show sunan items or empty search message
        if (filteredSunan.isNotEmpty()) {
          items(filteredSunan) { sunnah ->
            SunnahCard(
              sunnah.sunnah.title,
              sunnah.sunnah.quantity ?: "",
              sunnah.sunnah.hadith
            )
          }
        } else if (searchQuery.isNotBlank()) {
          // Show empty search message when search has no matches
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingLarge),
              contentAlignment = Alignment.Center
            ) {
              AnimatedVisibility(visible = true) {
                Text(
                  text = "لا توجد نتائج للبحث",
                  style = MaterialTheme.typography.bodyMedium
                )
              }
            }
          }
        }
      }
    }
  }
}
