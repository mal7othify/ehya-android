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
package com.eillia.ehya.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eillia.ehya.model.data.dao.InteractionDao
import com.eillia.ehya.model.data.dao.SunnahDao
import com.eillia.ehya.model.data.entity.Category
import com.eillia.ehya.model.data.entity.Interaction
import com.eillia.ehya.model.data.entity.Sunnah

@Database(
  entities = [
    Sunnah::class,
    Category::class,
    Interaction::class
  ],
  version = 1,
  exportSchema = true
)
abstract class EhyaDatabase : RoomDatabase() {
  abstract fun sunnahDao(): SunnahDao

  abstract fun interactionDao(): InteractionDao
}
