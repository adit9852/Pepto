plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.pepto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pepto"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
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
        compose = true
    }

    composeOptions {
        // kotlinCompilerExtensionVersion is managed by AGP
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.volley)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Media3 ExoPlayer dependencies
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.constraintlayout.compose)


    //Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    //Coil for image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    //Navigation Compose
    implementation(libs.androidx.navigation.compose)

    //Dagger Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt for jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    //Location Services
    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions)

    //Skydoves
    implementation(libs.cloudy)

    //Required Ktor Dependencies
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Razorpay
    implementation(libs.razorpay.checkout)

    // Required serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.firebase.appcheck.ktx)
    implementation(libs.firebase.appcheck.playintegrity)

    // logging interceptor
    implementation(libs.okhttp.logging.interceptor)
}
