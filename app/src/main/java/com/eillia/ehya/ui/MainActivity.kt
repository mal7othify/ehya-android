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
package com.eillia.ehya.ui

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eillia.ehya.navigation.BottomNavItems
import com.eillia.ehya.navigation.Navigation
import com.eillia.ehya.navigation.Routes
import com.eillia.ehya.ui.components.BottomBar
import com.eillia.ehya.ui.components.TopBar
import com.eillia.ehya.ui.theme.EhyaTheme
import com.eillia.ehya.viewmodels.AppViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val appViewModel: AppViewModel by viewModels()

  @OptIn(ExperimentalAnimationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, true)
    val pkgInfo = setupPackage()
    setupFCM()
    setContent {
      EhyaTheme {
        ProvideWindowInsets {
          CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Surface(color = MaterialTheme.colors.primary) {
              val navController = rememberNavController()
              val backStackEntry by navController.currentBackStackEntryAsState()
              val isInfoScreen =
                backStackEntry?.destination?.route == Routes.Info.route
              val isSplashScreen =
                backStackEntry?.destination?.route == Routes.Splash.route
              Scaffold(
                topBar = {
                  if (!isInfoScreen && !isSplashScreen) TopBar(
                    navController
                  )
                },
                bottomBar = {
                  if (!isInfoScreen && !isSplashScreen)
                    BottomBar(
                      navController = navController,
                      items = BottomNavItems,
                      onItemSelected = {
                        if (backStackEntry?.destination?.route != it.route)
                          navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                              saveState = true
                            }
                            launchSingleTop = true
                          }
                      }
                    )
                }
              ) {
                Navigation(navController, appViewModel, pkgInfo)
              }
            }
          }
        }
      }
    }
  }

  private fun setupFCM() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(
      OnCompleteListener { task ->
        if (!task.isSuccessful) {
          return@OnCompleteListener
        }
        val token: String? = task.result
      }
    )
  }

  private fun setupPackage(): PackageInfo {
    var pkgInfo: PackageInfo? = null
    try {
      pkgInfo = applicationContext.packageManager
        .getPackageInfo(applicationContext.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    return pkgInfo!!
  }
}
