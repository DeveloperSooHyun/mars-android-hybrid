plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.mars.hybrid.aos"
    compileSdk = 36

    flavorDimensions += "server"

    defaultConfig {
        applicationId = "com.mars.hybrid.aos"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    productFlavors {
        create("local") {
            dimension = "server"
            applicationIdSuffix = ".local"
            versionNameSuffix = "-local"

            resValue("string", "app_name", "Mars(Local)")
            buildConfigField("String", "SERVER_URL", "\"http://192.168.0.75:8080\"")
        }

        create("dev") {
            dimension = "server"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            resValue("string", "app_name", "Mars(Dev)")
            buildConfigField("String", "SERVER_URL", "\"http://192.168.0.75:8080\"")
        }

        create("prod") {
            dimension = "server"

            resValue("string", "app_name", "Mars")
            buildConfigField("String", "SERVER_URL", "\"https://\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // activity
    implementation(libs.material)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // splash
    implementation(libs.splash)

    // retrofit2
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    // okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // biometric
    implementation(libs.androidx.biometric)

    // swipe
    implementation(libs.swipe)
}