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
package com.eillia.ehya.helpers

import com.ehya.R
import com.eillia.ehya.model.data.entity.Sunnah

fun categories(sunnah: Sunnah): Int {
  when (sunnah.category.imageId) {
    "wudoo" -> return R.drawable.wudoo
    "general" -> return R.drawable.general
    "pray" -> return R.drawable.pray
    "sleep" -> return R.drawable.sleep
    "food" -> return R.drawable.food
    "appearance" -> return R.drawable.appearance
  }
  return 0
}
