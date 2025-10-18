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
package com.eillia.ehya.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

  NavigationBar(
    containerColor = mauve,
    modifier = modifier,
    tonalElevation = 0.dp
  ) {
    items.forEach { item ->
      val selected = item.route == backStackEntry?.destination?.route
      NavigationBarItem(
        selected = selected,
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.secondary,
          unselectedIconColor = MaterialTheme.colorScheme.surface,
          selectedTextColor = MaterialTheme.colorScheme.secondary,
          unselectedTextColor = MaterialTheme.colorScheme.surface,
          indicatorColor = Color.Transparent //  MaterialTheme.colorScheme.secondary
        ),
        onClick = { onItemSelected(item) },
        icon = {
          Column(horizontalAlignment = CenterHorizontally) {
            Icon(
              imageVector = item.icon,
              contentDescription = item.name,
              modifier = Modifier.size(32.dp)
            )
            AnimatedVisibility(selected) {
              Text(
                item.name,
                textAlign = TextAlign.Center, fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
              )
            }
          }
        },
        alwaysShowLabel = false,
      )
    }
  }
}
