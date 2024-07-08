/*
 * Copyright 2022
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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

@SuppressLint("ConflictingOnColor")
private val LightColorPalette =
  lightColors(
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
  darkTheme: Boolean = isSystemInDarkTheme(),
  content:
    @Composable()
    () -> Unit
) {
  // Remember a SystemUiController
  val systemUiController = rememberSystemUiController()

  val colors =
    if (darkTheme) {
      DarkColorPalette
    } else {
      LightColorPalette
    }

  SideEffect {
    systemUiController.setNavigationBarColor(
      color = Color.Transparent,
      navigationBarContrastEnforced = false,
      darkIcons = true
    )
  }

  MaterialTheme(
    colors = colors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}
