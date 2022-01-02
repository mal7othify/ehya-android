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
package com.eillia.ehya.ui.animation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatMultiStateAnimationCircleCanvas(color: Color, radiusEnd: Float) {
  val transition = rememberInfiniteTransition()
  val floatAnim by transition.animateFloat(
    initialValue = 10f,
    targetValue = radiusEnd,
    animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse)
  )

  Canvas(modifier = Modifier.padding(16.dp)) {
    val centerOffset = Offset(
      10f,
      10f
    )
    drawCircle(
      color = color.copy(alpha = 0.8f),
      radius = floatAnim,
      center = centerOffset,
    )
    drawCircle(
      color = color.copy(alpha = 0.4f),
      radius = floatAnim / 2,
      center = centerOffset,
    )
    drawCircle(
      color = color.copy(alpha = 0.2f),
      radius = floatAnim / 4,
      center = centerOffset,
    )
  }
  Spacer(modifier = Modifier.height(100.dp))
}
