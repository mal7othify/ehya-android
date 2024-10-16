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
package com.eillia.ehya.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eillia.ehya.navigation.BottomNavItem
import com.eillia.ehya.navigation.BottomNavItems
import com.eillia.ehya.navigation.Navigation
import com.eillia.ehya.navigation.Routes
import com.eillia.ehya.ui.components.BottomBar
import com.eillia.ehya.ui.components.TopBar
import com.eillia.ehya.ui.theme.EhyaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    enableEdgeToEdge()
    setContent {
      EhyaTheme {
        CompositionLocalProvider(
          LocalLayoutDirection provides LayoutDirection.Rtl,
          LocalRippleConfiguration provides null
        ) {
          val navController = rememberNavController()
          val backStackEntry by navController.currentBackStackEntryAsState()
          val isInfoScreen =
            backStackEntry?.destination?.route == Routes.Info.route
          val isSplashScreen =
            backStackEntry?.destination?.route == Routes.Splash.route
          Scaffold(
            topBar = {
              DrawTopAppBar(
                !isInfoScreen && !isSplashScreen,
                navController
              )
            },
            bottomBar = {
              if (!isInfoScreen && !isSplashScreen) {
                BottomBar(
                  navController = navController,
                  items = BottomNavItems,
                  onItemSelected = { bottomNavItem ->
                    onBottomBarItemSelected(
                      backStackEntry,
                      navController,
                      bottomNavItem
                    )
                  }
                )
              }
            }
          ) { paddingValues ->
            Navigation(
              navController = navController,
              contentPadding = paddingValues
            )
          }
        }
      }
    }
  }
}

@Composable
private fun DrawTopAppBar(
  shouldDraw: Boolean,
  navController: NavHostController
) {
  if (shouldDraw) {
    TopBar(navController)
  }
}

private fun onBottomBarItemSelected(
  backStackEntry: NavBackStackEntry?,
  navController: NavHostController,
  bottomNavItem: BottomNavItem
) {
  val isSelected =
    backStackEntry?.destination?.route != bottomNavItem.route
  if (isSelected) {
    navController.navigate(bottomNavItem.route) {
      popUpTo(
        navController.graph.findStartDestination().id
      ) {
        saveState = true
      }
      launchSingleTop = true
    }
  }
}
