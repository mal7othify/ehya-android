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
package com.eillia.ehya.ui.screens.play

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.eillia.ehya.helpers.categories
import com.eillia.ehya.model.data.entity.Category
import com.eillia.ehya.model.data.entity.Sunnah
import com.eillia.ehya.ui.animation.FloatMultiStateAnimationCircleCanvas
import com.eillia.ehya.ui.utils.Dimens

@ExperimentalAnimationApi
@Composable
fun CardContent(
  sunnah: Sunnah,
  trySunnah: (Sunnah) -> Unit,
  passSunnah: (Sunnah) -> Unit
) {
  val scrollState = rememberScrollState(0)
  var expandedState by remember { mutableStateOf(false) }
  var favoriteState by remember { mutableStateOf(false) }
  val category = categories(sunnah)
  Column(
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier =
      Modifier
        .background(
          brush =
            Brush.verticalGradient(
              colors =
                listOf(
                  Color(0xFFD2AFB9),
                  Color(0xFFAAAEB3)
                ),
              endY = (LocalConfiguration.current.screenHeightDp.dp.value / 2)
            )
        ).padding(16.dp)
        .padding(bottom = 30.dp)
        .verticalScroll(scrollState)
  ) {
    Text(
      text = sunnah.title,
      modifier = Modifier.fillMaxWidth(),
      style = typography.h1
    )
    Text(
      text = sunnah.quantity!!,
      modifier = Modifier.fillMaxWidth(),
      style = typography.caption,
      textAlign = TextAlign.Center
    )
    Image(
      imageVector = ImageVector.vectorResource(category),
      modifier =
        Modifier
          .requiredSize(Dimens.ImageSize)
          .padding(8.dp),
      contentDescription = null,
      contentScale = ContentScale.Crop
    )
    Text(
      text = sunnah.category.title,
      style =
        typography.body2.copy(
          background = MaterialTheme.colors.secondary
        )
    )
    Text(
      text = sunnah.hadith,
      modifier = Modifier.fillMaxWidth(),
      lineHeight = 1.25.em,
      style = typography.body1
    )
    Text(
      text = sunnah.strength,
      modifier =
        Modifier
          .fillMaxWidth()
          .wrapContentHeight(),
      lineHeight = 1.25.em,
      style = typography.caption.copy(fontSize = 12.sp)
    )
    Column(
      modifier =
        Modifier
          .fillMaxWidth()
          .wrapContentHeight()
          .clip(MaterialTheme.shapes.medium)
          .clickable(
            onClick = {
              expandedState = !expandedState
            }
          ).padding(8.dp)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = com.ehya.R.string.howto),
        style = typography.body1.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
      )
      Image(
        modifier =
          Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
        imageVector =
          if (expandedState) {
            Icons.Filled.ExpandLess
          } else {
            Icons.Filled.ExpandMore
          },
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
      )
      AnimatedVisibility(
        visible = expandedState,
        enter = expandIn() + expandVertically(),
        exit = shrinkOut() + shrinkVertically()
      ) {
        Text(
          modifier = Modifier.wrapContentSize(),
          text = sunnah.howto,
          style = typography.caption,
          lineHeight = 1.em
        )
      }
    }
    Box(
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(8.dp)
    ) {
      IconButton(
        onClick = { trySunnah(sunnah) },
        modifier =
          Modifier
            .padding(start = 16.dp)
            .align(Alignment.CenterStart)
      ) {
        FloatMultiStateAnimationCircleCanvas(
          MaterialTheme.colors.secondary,
          100f
        )
        Icon(
          imageVector = Icons.Rounded.ArrowForward,
          tint = Color.Green,
          contentDescription = null,
          modifier = Modifier.requiredSize(50.dp)
        )
      }
      Text(
        text = stringResource(id = com.ehya.R.string.tryit),
        modifier =
          Modifier
            .align(Alignment.TopStart)
            .padding(start = 25.dp, bottom = 100.dp)
      )
//            IconButton(
//                onClick = { favoriteState = !favoriteState },
//                modifier = Modifier
//                    .padding(end = 16.dp)
//                    .align(Alignment.Center)
//            ) {
//                Icon(
//                    imageVector = if (favoriteState) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
//                    tint = mauve,
//                    contentDescription = null,
//                    modifier = Modifier.requiredSize(50.dp)
//                )
//            }
      IconButton(
        onClick = { passSunnah(sunnah) },
        modifier =
          Modifier
            .padding(end = 16.dp)
            .align(Alignment.CenterEnd)
      ) {
        FloatMultiStateAnimationCircleCanvas(
          MaterialTheme.colors.secondary,
          100f
        )
        Icon(
          imageVector = Icons.Rounded.ArrowBack,
          tint = Color.Red,
          contentDescription = null,
          modifier = Modifier.requiredSize(50.dp)
        )
      }
      Text(
        text = stringResource(id = com.ehya.R.string.passit),
        modifier =
          Modifier
            .align(Alignment.TopEnd)
            .padding(end = 25.dp, bottom = 100.dp)
      )
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun CardContentPreview() {
  val sunnah =
    Sunnah(
      0,
      "إخبار من تحبهم أنك تحبهم",
      "طرق الباب ثلاث مرات",
      Category("عام", "general", ""),
      "النبي -صلى الله عليه وسلم- قال: (إذا أحبَّ أحدُكم أخاهُ فليُعلمْهُ أنَّهُ يحبُّهُ).",
      "الراوي : المقدام بن معدي كرب | المحدث : الألباني | المصدر : السلسلة الصحيحة",
      "إخبار من تحبهم أنك تحبهم."
    )
  CardContent(sunnah = sunnah, trySunnah = {}, passSunnah = {})
}
