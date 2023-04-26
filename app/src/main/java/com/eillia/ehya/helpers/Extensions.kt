package com.eillia.ehya.helpers

import android.content.Context
import android.content.Intent
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
