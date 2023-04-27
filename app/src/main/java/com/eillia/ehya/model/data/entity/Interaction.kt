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
package com.eillia.ehya.model.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "interactionTable",
  foreignKeys = [
    ForeignKey(
      entity = Sunnah::class,
      parentColumns = ["id"],
      childColumns = ["sunnahId"]
    )
  ],
  indices = [Index("sunnahId")]
)
data class Interaction(
  val sunnahId: Int,
  var isTried: Boolean = false,
  var isPassed: Boolean = false,
  val tries: Int = 0,
  val passes: Int = 0,
  val isFavorite: Boolean = false
) {
  @PrimaryKey(autoGenerate = true)
  var interactionId: Int = 1
}
