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
package com.eillia.ehya.model.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eillia.ehya.model.data.item.SwipeResult

@Entity(
  tableName = "Sunan",
  foreignKeys = [
    ForeignKey(
      entity = Category::class,
      parentColumns = ["id"],
      childColumns = ["category_id"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index("category_id")]
)
data class Sunnah(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,
  val title: String,
  val quantity: String?,
  @ColumnInfo(name = "category_id")
  val categoryId: Int,
  val hadith: String,
  val strength: String,
  val howto: String,
  @ColumnInfo(defaultValue = "NONE")
  var swipeResult: SwipeResult? = SwipeResult.NONE
)
