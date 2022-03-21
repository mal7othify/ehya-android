package com.eillia.ehya.helpers

import android.content.Context
import android.content.Intent
import com.ehya.R

fun shareApp(appId: String, context: Context) {
  val intent = Intent(Intent.ACTION_SEND)
  intent.type = "text/plain"
  intent.putExtra(
    Intent.EXTRA_TEXT,
    String.format(
      context.resources.getString(R.string.shareapp),
      "https://play.google.com/store/apps/details?id=$appId"
    )
  )
  context.startActivity(Intent.createChooser(intent, "مشاركة التطبيق"))
}
