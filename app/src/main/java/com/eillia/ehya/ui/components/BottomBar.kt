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
package com.eillia.ehya.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eillia.ehya.navigation.BottomNavItem
import com.eillia.ehya.ui.theme.mauve

@Composable
fun BottomBar(
  navController: NavController,
  items: List<BottomNavItem>,
  modifier: Modifier = Modifier,
  onItemSelected: (BottomNavItem) -> Unit
) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  BottomNavigation(
    backgroundColor = mauve,
    modifier = modifier,
    elevation = 0.dp
  ) {
    items.forEach { item ->
      val selected = item.route == backStackEntry?.destination?.route
      BottomNavigationItem(
        selected = selected,
        selectedContentColor = MaterialTheme.colors.secondary,
        unselectedContentColor = MaterialTheme.colors.surface,
        onClick = { onItemSelected(item) },
        icon = {
          Column(horizontalAlignment = CenterHorizontally) {
            Icon(item.icon, contentDescription = item.name)
            if (selected)
              Text(item.name, textAlign = TextAlign.Center, fontSize = 12.sp)
          }
        },
      )
    }
  }
}
