
plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/AL2.0","META-INF/LGPL2.1"))
    }
}

dependencies {
    implementation(project(Modules.components))
    implementation(project(Modules.core))
    implementation(project(Modules.subjectInteractors))
    implementation(project(Modules.ui_subjectDetail))
    implementation(project(Modules.ui_subjectList))

    implementation(Accompanist.navigationAnimation)
    implementation(Accompanist.pager)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleViewModelKtx)

    implementation(Coil.coil)

    implementation(Compose.activityCompose)
    implementation(Compose.hiltNavigation)
    implementation(Compose.iconsExtended)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.tooling)
    implementation(Compose.ui)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(SqlDelight.androidDriver)

    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(ComposeTest.uiTestJunit4)
    androidTestImplementation(ComposeTest.uiTestManifest)
    kaptAndroidTest(Hilt.compiler)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    androidTestImplementation(Junit.junit4)
}
