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
package com.eillia.ehya.di

import android.content.Context
import com.eillia.ehya.model.data.dao.InteractionDao
import com.eillia.ehya.model.data.dao.SunnahDao
import com.eillia.ehya.model.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun providesDatabase(
    @ApplicationContext context: Context
  ): AppDatabase {
    return AppDatabase.getInstance(context)
  }

  @Singleton
  @Provides
  fun provideSunnahDao(db: AppDatabase): SunnahDao {
    return db.sunnahDao()
  }

  @Singleton
  @Provides
  fun provideInteractionDao(db: AppDatabase): InteractionDao {
    return db.interactionDao()
  }
}
