/*
 * Copyright 2024 Maryam Alhuthayfi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Lists all plugins used throughout the project
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.compose) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.dependencyGuard) apply false
  alias(libs.plugins.gms) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.secrets) apply false
  alias(libs.plugins.room) apply false
  alias(libs.plugins.module.graph) apply true
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.spotless) apply false
}

allprojects {
  // Configure Spotless for all subprojects
  if (this == rootProject) {
    return@allprojects
  }

  // Spotless configuration for each subproject
  apply<com.diffplug.gradle.spotless.SpotlessPlugin>()

  extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt", "**/*.kts")
      targetExclude("**/build/**/*.kt", "**/build/**/*.kts")
      licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    }
    format("xml") {
      target("**/*.xml")
      targetExclude("**/build/**/*.xml")
      licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
    }
  }
}
