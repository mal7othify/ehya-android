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

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ehya.R
import com.eillia.ehya.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
  navController: NavController,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    modifier =
      modifier
        .fillMaxWidth()
        .heightIn(max = 85.dp),
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
    ),
    title = {
      Row(
        modifier =
          modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          modifier = Modifier
            .size(24.dp)
            .clickable {
              navController.navigate(Routes.Info.route)
            },
          imageVector = Icons.Filled.Info,
          tint = MaterialTheme.colorScheme.secondary,
          contentDescription = "more information"
        )

        Text(
          text = stringResource(R.string.app_name),
          modifier = Modifier.align(Alignment.CenterVertically),
          fontWeight = FontWeight.ExtraBold,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.surface,
          )
        )

        Image(
          modifier = Modifier.size(52.dp),
          imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
          contentDescription = "Ehya logo",
          alignment = Alignment.TopEnd
        )

        /*        if (isSunanScreen) {
                  IconButton(onClick = { filtered = !filtered }) {
                    Icon(
                      imageVector = if (filtered) Icons.Default.Close else Icons.Rounded.FilterList,
                      tint = MaterialTheme.colors.secondary,
                      contentDescription = "Filter sunan"
                    )
                  }
                } else {
                  Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
                    contentDescription = "Ehya logo",
                    alignment = Alignment.TopEnd
                  )
                }*/
      }
    }
  )
}
