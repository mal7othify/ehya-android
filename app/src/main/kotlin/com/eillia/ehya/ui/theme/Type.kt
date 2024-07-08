/*
 * Copyright 2022
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
package com.eillia.ehya.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ehya.R

private val Tajawal =
  FontFamily(
    listOf(
      Font(R.font.tajawal_regular),
      Font(R.font.tajawal_medium, FontWeight.W500),
      Font(R.font.tajawal_bold, FontWeight.Bold)
    )
  )

// Set of Material typography styles to start with
val typography =
  Typography(
    h1 =
      TextStyle(
        fontFamily = Tajawal,
        fontWeight = FontWeight.W300,
        textAlign = TextAlign.Center,
        fontSize = 18.sp
      ),
    h2 =
      TextStyle(
        fontFamily = Tajawal,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
      ),
    body1 =
      TextStyle(
        fontFamily = Tajawal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        textAlign = TextAlign.Justify
      ),
    body2 =
      TextStyle(
        color = Color.DarkGray,
        fontSize = 22.sp,
        fontFamily = FontFamily(listOf(Font(R.font.arslan_wessam)))
      ),
    button =
      TextStyle(
        fontFamily = Tajawal,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
      ),
    caption =
      TextStyle(
        fontFamily = Tajawal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
      )
  )
