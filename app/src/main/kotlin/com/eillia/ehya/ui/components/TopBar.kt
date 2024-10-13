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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ehya.R
import com.eillia.ehya.navigation.Routes

@Composable
fun TopBar(navController: NavController) {
  var filtered by remember { mutableStateOf(false) }
  val backStackEntry by navController.currentBackStackEntryAsState()
  val isSunanScreen = backStackEntry?.destination?.route == Routes.Sunan.route
  TopAppBar(
    backgroundColor = Color.Transparent,
    elevation = 0.dp
  ) {
    Row(
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = { navController.navigate(Routes.Info.route) }) {
        Icon(
          imageVector = Icons.Filled.Info,
          tint = MaterialTheme.colors.secondary,
          contentDescription = "more information"
        )
      }
      Text(
        text = stringResource(R.string.app_name),
        modifier = Modifier.align(Alignment.CenterVertically),
        fontWeight = FontWeight.ExtraBold
      )
      Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
        contentDescription = "Ehya logo",
        alignment = Alignment.TopEnd
      )
      /*
//            if (isSunanScreen) {
//                IconButton(onClick = { filtered = !filtered }) {
//                    Icon(
//                        imageVector = if (filtered) Icons.Default.Close else Icons.Rounded.FilterList,
//                        tint = MaterialTheme.colors.secondary,
//                        contentDescription = "Filter sunan"
//                    )
//                }
//            } else
//                Image(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
//                    contentDescription = "Ehya logo",
//                    alignment = Alignment.TopEnd
//                )*/
    }
  }
}
