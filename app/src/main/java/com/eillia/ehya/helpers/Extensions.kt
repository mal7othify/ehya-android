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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

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
    pkgInfo = this.packageManager
      .getPackageInfo(this.packageName, 0)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
  }
  return pkgInfo!!
}

fun setupFCM() {
  FirebaseMessaging.getInstance().token.addOnCompleteListener(
    OnCompleteListener { task ->
      if (!task.isSuccessful) {
        return@OnCompleteListener
      }
      val token: String? = task.result
    }
  )
}

object NoRippleTheme : RippleTheme {
  @Composable
  override fun defaultColor() = Color.Unspecified

  @Composable
  override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}
