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
package com.eillia.ehya.navigation

import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eillia.ehya.ui.screens.info.InfoScreen
import com.eillia.ehya.ui.screens.play.PlayScreen
import com.eillia.ehya.ui.screens.splash.SplashScreen
import com.eillia.ehya.ui.screens.sunan.SunanScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
  navController: NavHostController,
  contentPadding: PaddingValues
) {
  val shouldShowSplash = Build.VERSION.SDK_INT <= Build.VERSION_CODES.R
  val startDestination =
    if (shouldShowSplash) {
      Routes.Splash.route
    } else {
      Routes.Play.route
    }
  NavHost(navController = navController, startDestination = startDestination) {
    composable(Routes.Splash.route) {
      SplashScreen(navController = navController)
    }
    composable(Routes.Play.route) {
      PlayScreen(contentPadding = contentPadding)
    }
    composable(Routes.Sunan.route) {
      SunanScreen(contentPadding = contentPadding)
    }
    composable(Routes.Info.route) {
      InfoScreen(navController = navController)
    }
  }
}
