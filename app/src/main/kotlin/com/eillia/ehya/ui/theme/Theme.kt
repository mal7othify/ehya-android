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
package com.eillia.ehya.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import com.eillia.ehya.ui.utils.handleStatusAndNavigationBars

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette =
  darkColors(
    primary = blackOlive,
    primaryVariant = blackish,
    secondary = yellowL,
    background = blackOlive,
    surface = gainsboro,
    onPrimary = gainsboro,
    onSecondary = blackOlive,
    onBackground = blackOlive,
    onSurface = blackOlive,
    onError = gainsboro
  )

@Composable
fun EhyaTheme(
  content:
    @Composable()
    () -> Unit
) {
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.handleStatusAndNavigationBars(
        view = view,
        statusBarColor = DarkColorPalette.primary.toArgb(),
        navigationBarColor = Color.Transparent.toArgb()
      )
    }
  }

  MaterialTheme(
    colors = DarkColorPalette,
    typography = typography,
    shapes = shapes,
    content = content
  )
}
