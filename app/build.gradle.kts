import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("./keystore.properties")
val canSignWithKeystore = keystorePropertiesFile.exists()

if (canSignWithKeystore) {
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.gms) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.compose)
  id("dagger.hilt.android.plugin")
}

android {
  namespace = "com.ehya"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.ehya"
    minSdk = 24
    targetSdk = 35
    versionCode = 28
    versionName = "2.1.4"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }

    ksp {
      arg("room.schemaLocation", "$projectDir/schemas")
    }
    javaCompileOptions {
      annotationProcessorOptions {
        arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
      }
    }
  }

  signingConfigs {
    if (canSignWithKeystore) {
      create("release") {
        storeFile = keystoreProperties["prodKeystoreFile"]?.let { file(it) }
        storePassword = keystoreProperties["prodKeystorePassword"] as String
        keyAlias = keystoreProperties["prodKeyAlias"] as String
        keyPassword = keystoreProperties["prodKeyPassword"] as String
      }
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      signingConfig =
        if (canSignWithKeystore) {
          signingConfigs.named("debug").get()
        } else {
          signingConfigs.named("release").get()
        }
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
    debug {
      isMinifyEnabled = false
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures {
    buildConfig = true
    compose = true
  }

  packaging.resources {
    // Multiple dependency bring these files in. Exclude them to enable
    // our test APK to build (has no effect on our AARs)
    excludes += "/META-INF/AL2.0"
    excludes += "/META-INF/LGPL2.1"
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  tasks
    .withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>()
    .configureEach {
      jvmTargetValidationMode
        .set(
          org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING
        )
      compilerOptions
        .languageVersion
        .set(
          org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
        )
    }

  applicationVariants.all {
    val variant = this
    variant.outputs
      .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
      .forEach { output ->
        val outputFileName = "ehya-$versionName($versionCode).apk"
        output.outputFileName = outputFileName
      }
  }
}

dependencies {
  implementation(libs.kotlin.stdlib)

  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.activity.ktx)

  val composeBom = platform(libs.androidx.compose.bom)
  implementation(composeBom)
  androidTestImplementation(composeBom)

  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.foundation.layout)
  implementation(libs.androidx.compose.ui.ui)
  implementation(libs.androidx.compose.ui.util)
//  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.material.iconsExtended)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.ui.tooling.preview)
  debugImplementation(libs.androidx.compose.ui.tooling)

  implementation(libs.accompanist.insets)
  implementation(libs.accompanist.systemuicontroller)

  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.lifecycle.viewModelCompose)
  implementation(libs.androidx.work.ktx)

  implementation(libs.room.ktx)
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  ksp(libs.hilt.ext.compiler)

  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.cloud.messaging)
  implementation(libs.firebase.analytics)
  implementation(libs.firebase.crashlytics)

  implementation(libs.code.gson)

  implementation(libs.timber)

  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.androidx.test.ext)

  coreLibraryDesugaring(libs.core.jdk.desugaring)
  kspAndroidTest(libs.hilt.compiler)
}
