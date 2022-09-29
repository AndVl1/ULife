import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
    kotlin("plugin.serialization")
    id("androidx.navigation.safeargs") version "2.5.2"
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
            it.buildConfigField("String", "API_URL","\"http://37.139.33.65:8080/\"")
            it.buildConfigField(
                "String",
                "IMGBB_API_KEY",
                "\"" +
                        (if (imgbbKey.isNullOrEmpty()) {
                            System.getenv("IMGBB_API_KEY")
                        } else {
                            imgbbKey
                        }) +
                        "\""
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/*"
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
    implementation(libs.android.fragment)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.coroutines)
    implementation(libs.googlemaps.common)
    implementation(libs.googlemaps.compose)
    implementation(libs.play.location)
    implementation(libs.compose.accompanist.permissions)
    implementation(libs.compose.coil)
    implementation(libs.compose.dialogs)
    implementation(libs.compose.time.dialog)
    implementation(libs.compose.accompanist.pager)
    implementation(libs.compose.accompanist.pagerindicator)
    implementation(libs.compose.accompanist.swiperefresh)
    implementation(libs.compose.accompanist.placeholder)
    implementation(libs.compose.accompanist.systemui)
    implementation(projects.uicommon)
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")

    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.5.2")

    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.fragment:fragment-ktx:1.5.3")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation("com.google.android.material:material:1.8.0-alpha01")

    implementation("com.github.bumptech.glide:glide:4.12.0")

    implementation("io.insert-koin:koin-android-compat:3.2.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
}
