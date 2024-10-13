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
package com.eillia.ehya.helpers

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ehya.R

fun Context.shareApp(appId: String) {
  val intent = Intent(Intent.ACTION_SEND)
  intent.type = "text/plain"
  intent.putExtra(
    Intent.EXTRA_TEXT,
    String.format(
      this.resources.getString(R.string.shareapp),
      "https://play.google.com/store/apps/details?id=$appId"
    )
  )
  this.startActivity(Intent.createChooser(intent, "مشاركة التطبيق"))
}

fun Context.setupPackage(): PackageInfo {
  var pkgInfo: PackageInfo? = null
  try {
    pkgInfo =
      this.packageManager
        .getPackageInfo(this.packageName, 0)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
  }
  return pkgInfo!!
}

object NoRippleTheme : RippleTheme {
  @Composable
  override fun defaultColor() = Color.Unspecified

  @Composable
  override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}
