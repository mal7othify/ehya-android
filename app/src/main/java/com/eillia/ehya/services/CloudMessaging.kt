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
package com.eillia.ehya.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ehya.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessaging : FirebaseMessagingService() {
  override fun onNewToken(token: String) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    initNotificationChannel(notificationManager)
  }

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    val title = remoteMessage.notification?.title
    val body = remoteMessage.notification?.body
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    var notificationBuilder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
      NotificationCompat.Builder(applicationContext, "1")
    } else {
      NotificationCompat.Builder(applicationContext)
    }
    notificationBuilder = notificationBuilder.setSmallIcon(R.drawable.ic_logo)
      .setContentTitle(title)
      .setContentText(body)
      .setAutoCancel(true)

    initNotificationChannel(notificationManager)
    notificationManager.notify(DEFAULT_NOTIFICATION__ID, notificationBuilder.build())
  }

  private fun initNotificationChannel(notificationManager: NotificationManager) {
    if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
      notificationManager.createNotificationChannelIfNotExists(
        "1",
        "Default"
      )
    }
  }

  companion object {
    const val DEFAULT_NOTIFICATION__ID = 0
  }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationManager.createNotificationChannelIfNotExists(
  channelId: String,
  channelName: String,
  importance: Int = NotificationManager.IMPORTANCE_DEFAULT
) {
  var channel = this.getNotificationChannel(channelId)
  if (channel == null) {
    channel = NotificationChannel(
      channelId,
      channelName,
      importance
    )
    this.createNotificationChannel(channel)
  }
}
