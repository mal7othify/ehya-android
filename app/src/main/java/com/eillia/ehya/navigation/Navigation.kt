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
package com.eillia.ehya.navigation

import android.content.pm.PackageInfo
import androidx.compose.animation.ExperimentalAnimationApi
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
  pkgInfo: PackageInfo
) {
  NavHost(navController = navController, startDestination = Routes.Splash.route) {
    composable(Routes.Splash.route) {
      SplashScreen(navController)
    }
    composable(Routes.Play.route) {
      PlayScreen()
    }
    composable(Routes.Sunan.route) {
      SunanScreen()
    }
    composable(Routes.Info.route) {
      InfoScreen(navController, pkgInfo)
    }
  }
}
