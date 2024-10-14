plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation(libs.secrets.gradle.plugin)
    implementation(libs.play.services.maps.v1810)
    implementation(libs.play.services.maps)
    implementation(libs.appcompat)
    implementation(libs.multidex)
    implementation(libs.core.ktx.v190)
    implementation(libs.appcompat.v161)
    implementation(libs.core.ktx)
    // Google Play Services for location
    implementation(libs.play.services.location.v2101)
    // Gson for JSON parsing
    implementation(libs.gson)
    // Optional: For network requests (e.g., Retrofit)
     implementation(libs.retrofit)
    // Optional: For image loading (e.g., Glide)
     implementation(libs.glide)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.location)
    implementation(libs.leanback)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}