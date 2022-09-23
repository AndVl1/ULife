import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
    kotlin("plugin.serialization")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "ru.bmstu.ulife"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["maps_api_key"] = kotlin.run {
            val mapKey = gradleLocalProperties(rootDir).getProperty("maps_api_key")
            return@run if (mapKey.isNullOrEmpty()) {
                System.getenv("MAPS_API_KEY")
            } else {
                mapKey
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        val imgbbKey = gradleLocalProperties(rootDir).getProperty("IMGBB_API_KEY")
        forEach {
            it.buildConfigField(
                "String",
                "IMGBB_API_KEY",
                if (imgbbKey.isNullOrEmpty()) {
                    System.getenv("MAPS_API_KEY")
                } else {
                    imgbbKey
                }
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.android.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.tooling)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.compose.activity)
    implementation(libs.okhttp)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.json)
    implementation(libs.ktor.negotiation)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.coroutines)
    implementation(libs.googlemaps.common)
    implementation(libs.googlemaps.compose)
    implementation(libs.play.location)
    implementation(libs.compose.accompanist.permissions)
    implementation(libs.compose.coil)
    implementation(libs.compose.dialogs)
    implementation(libs.compose.time.dialog)
    implementation(projects.uicommon)
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.github.tony19:logback-android:2.0.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")
}
