package com.eillia.ehya.model.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SunnahWithCategory(
  @Embedded
  val sunnah: Sunnah,
  @Relation(
    parentColumn = "category_id", // Column in the Sunan table (foreign key)
    entityColumn = "id" // Column in the Categories table (primary key)
  )
  val category: Category
)
