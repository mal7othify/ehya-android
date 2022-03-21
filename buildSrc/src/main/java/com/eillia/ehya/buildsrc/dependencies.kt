package com.eillia.ehya.buildsrc

object Versions {
  const val ktlint = "0.41.0"
}

object Libs {
  const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.2"
  const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

  object Accompanist {
    const val version = "0.16.0"
    const val insets = "com.google.accompanist:accompanist-insets:$version"
    const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
  }

  object Kotlin {
    const val version = "1.6.0"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

    object Coroutines {
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    }
  }

  object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val coreKtx = "androidx.core:core-ktx:1.6.0"

    object Activity {
      const val activityCompose = "androidx.activity:activity-compose:1.3.1"
      const val activityKtx = "androidx.activity:activity-ktx:1.4.0"
    }

    object ViewModel {
      private const val version = "2.4.0"
      const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
      const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
      const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    }

    object Compose {
      const val snapshot = ""
      const val version = "1.1.0-rc01"

      @get:JvmStatic
      val snapshotUrl: String
        get() = "https://androidx.dev/snapshots/builds/$snapshot/artifacts/repository/"

      const val runtime = "androidx.compose.runtime:runtime:$version"
      const val foundation = "androidx.compose.foundation:foundation:${version}"
      const val layout = "androidx.compose.foundation:foundation-layout:${version}"

      const val ui = "androidx.compose.ui:ui:${version}"
      const val material = "androidx.compose.material:material:${version}"
      const val materialIconsExtended =
        "androidx.compose.material:material-icons-extended:${version}"
      const val materialIconsCore = "androidx.compose.material:material-icons-core:${version}"
      const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${version}"
      const val tooling = "androidx.compose.ui:ui-tooling:${version}"
      const val navigation = "androidx.navigation:navigation-compose:${version}"
    }

    object Lifecycle {
      private const val version = "2.3.1"
      const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
    }

    object Room {
      private const val version = "2.4.0"
      private const val viewModelCompose = ""
      const val roomKtx = "androidx.room:room-ktx:$version"
      const val runtime = "androidx.room:room-runtime:$version"
      const val compiler = "androidx.room:room-compiler:$version"
    }

    object WorkManager {
      private const val version = "2.7.1"
      const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
    }

    object Navigation {
      private const val version = "2.4.0-rc01"
      const val compose = "androidx.navigation:navigation-compose:$version"
    }

    object Test {
      private const val version = "1.4.0"
      private const val androidXTestVersion = "1.4.0-rc01"
      private const val testJunitVersion = "1.1.4-alpha03"
      const val core = "androidx.test:core:$androidXTestVersion"
      const val junitRunner = "androidx.test:runner:$version"
      const val junitRules = "androidx.test:rules:$version"
      const val extJunit = "androidx.test.ext:junit:$testJunitVersion"
      const val extTruth = "androidx.test.ext:truth:$version"
    }
  }

  object Timber {
    private const val version = "4.7.1"
    const val timber = "com.jakewharton.timber:timber:$version"
  }

  object Hilt {
    private const val version = "2.38.1"
    private const val viewModelVersion = "1.0.0-alpha03"

    const val daggerHiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val daggerHilt = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$viewModelVersion"
  }

  object Gson {
    const val gson = "com.google.code.gson:gson:2.8.8"
  }

  object Play {
    private const val version = "1.8.1"
    const val coreKtx = "com.google.android.play:core-ktx:$version"
  }

  object Firebase {
    private const val gmsPluginVersion = "4.3.10"
    private const val crashlyticsPluginVersion = "2.8.1"
    private const val version = "29.0.3"
    const val gmsPlugin = "com.google.gms:google-services:$gmsPluginVersion"
    const val bom = "com.google.firebase:firebase-bom:$version"
    const val fcm = "com.google.firebase:firebase-messaging-ktx"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    const val crashlyticsPlugin =
      "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsPluginVersion"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
  }

  object JUnit {
    private const val version = "4.12"
    const val junit = "junit:junit:$version"
  }
}
