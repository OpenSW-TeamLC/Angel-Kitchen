plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Constants.compileSdk)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Constants.packageName
        minSdkVersion(Constants.minSdk)
        targetSdkVersion(Constants.targetSdk)
        versionCode(Constants.versionCode)
        versionName(Constants.versionName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // Kotlin
    implementation(KotlinDependencies.kotlin)

    // AndroidX
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.hilt)
    kapt(KaptDependencies.hiltCompiler)
    implementation(AndroidXDependencies.fragment)
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.navigationUI)
    implementation(AndroidXDependencies.navigation)
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.3.5")

    // Material Design
    implementation(MaterialDesignDependencies.materialDesign)

    // Libraray => Coil로 변경
    implementation(LibraryDependencies.glide)
    kapt(KaptDependencies.glide)

    // Test
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // android-stepper (Not implementation 0.3.0)
    implementation("com.github.acefalobi:android-stepper:0.2.2")

    // Ted Permission
    implementation("io.github.ParkSangGwon:tedpermission:2.3.0")

    // Lottie
    implementation("com.airbnb.android:lottie:3.7.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-alpha03")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha03")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0-alpha03")

    // Coil
    implementation("io.coil-kt:coil:0.10.0")

    // Carousel
    implementation("com.github.sparrow007:carouselrecyclerview:1.2.1")

    // Bottom Sheet
    implementation("com.github.HeyAlex:BottomDrawer:v1.0.0")

    // Naver Map
    implementation("com.naver.maps:map-sdk:3.12.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Room
    implementation("androidx.room:room-runtime:2.4.0-alpha04")
    kapt("androidx.room:room-compiler:2.4.0-alpha04")
    implementation("androidx.room:room-ktx:2.3.0")

    // Location
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.google.android.gms:play-services-places:17.0.0")

    // License
    implementation("com.github.colinrtwhite:licensesdialog:2.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
}