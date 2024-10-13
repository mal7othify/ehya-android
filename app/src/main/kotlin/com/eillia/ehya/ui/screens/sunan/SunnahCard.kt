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
package com.eillia.ehya.ui.screens.sunan

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.eillia.ehya.ui.theme.SunnahCardShape
import com.eillia.ehya.ui.theme.mauve
import com.eillia.ehya.ui.utils.Dimens

@Composable
fun SunnahCard(
  title: String,
  quantity: String,
  hadith: String
) {
  val context = LocalContext.current
  Card(
    modifier =
      Modifier
        .padding(Dimens.PaddingSmall)
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.Transparent),
    shape = SunnahCardShape,
    elevation = 5.dp
  ) {
    Column(
      modifier =
        Modifier
          .background(
            brush =
              Brush.horizontalGradient(
                colors =
                  listOf(
                    Color(0xFFAAAEB3),
                    Color(0xFFD2AFB9)
                  ),
                endX = (LocalConfiguration.current.screenHeightDp.dp.value / 0.2f)
              )
          ).padding(Dimens.PaddingNormal)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          title,
          style =
            MaterialTheme.typography.h1.copy(
              fontWeight = FontWeight.Bold,
              color = mauve,
              fontSize = 16.sp,
              textAlign = TextAlign.Right,
              lineHeight = 1.30.em
            )
        )
        IconButton(
          onClick = {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
              Intent.EXTRA_TEXT,
              "جرّب السنة المهجورة التالية: $title | $hadith"
            )
            context.startActivity(Intent.createChooser(intent, "مشاركة السنة"))
          }
        ) {
          Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Rounded.Share,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "مشاركة السنة"
          )
        }
      }

      if (quantity != "") {
        Text(
          text = quantity,
          modifier = Modifier.fillMaxWidth(),
          style =
            MaterialTheme.typography.caption.copy(
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.Bold,
              lineHeight = 1.30.em
            )
        )
      }
      Text(hadith, style = MaterialTheme.typography.body1, lineHeight = 1.25.em)
    }
  }
}
