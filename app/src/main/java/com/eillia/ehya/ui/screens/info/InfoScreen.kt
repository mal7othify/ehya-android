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
package com.eillia.ehya.ui.screens.info

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ehya.R
import com.eillia.ehya.ui.utils.Dimens

@Composable
fun InfoScreen(navController: NavController, pkgInfo: PackageInfo) {
  val context = LocalContext.current
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(Dimens.PaddingNormal),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      Icon(
        modifier = Modifier
          .size(25.dp)
          .clip(CircleShape)
          .clickable {
            navController.navigateUp()
          },
        imageVector = Icons.Rounded.ArrowBack,
        tint = MaterialTheme.colors.secondary,
        contentDescription = "Filter sunan"
      )
    }
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.requiredHeight(20.dp))
      Text(
        stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.h1.copy(
          fontSize = 20.sp,
          color = MaterialTheme.colors.onPrimary,
          textAlign = TextAlign.Center,
          fontWeight = FontWeight.Bold
        )
      )
      Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
        modifier = Modifier.requiredSize(Dimens.ImageSize),
        contentDescription = "Ehya logo",
      )
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(Dimens.PaddingNormal),
        text = stringResource(id = R.string.play),
        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
      )
      Text(
        modifier = Modifier.padding(Dimens.PaddingNormal),
        text = stringResource(id = R.string.playsteps),
        style = MaterialTheme.typography.body1,
        lineHeight = 1.9.em
      )
      Spacer(modifier = Modifier.requiredHeight(30.dp))
      Text(
        modifier = Modifier.padding(Dimens.PaddingNormal),
        text = "للتواصل عبر تويتر: mal7othify@",
        style = MaterialTheme.typography.body1,
        lineHeight = 1.9.em
      )
      Spacer(modifier = Modifier.requiredHeight(20.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          "شارك/ـي التطبيق مع أصدقائك ومعارفك",
          style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        IconButton(
          onClick = {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
              Intent.EXTRA_TEXT,
              "تحميل تطبيق إحياء لاستعراض السنن المهجورة من متجر جوجل بلاي: https://play.google.com/store/apps/details?id=com.eillia.ehya \n"
            )
            context.startActivity(Intent.createChooser(intent, "مشاركة التطبيق"))
          }
        ) {
          Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Rounded.Share,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "مشاركة التطبيق"
          )
        }
      }
    }
    Text(
      text = "إصدار البرنامج: ${pkgInfo.versionName}",
      modifier = Modifier.align(Alignment.BottomCenter)
    )
  }
}
