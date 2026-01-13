plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "com.alain.foundyou"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.alain.foundyou"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Seguridad
    implementation(libs.security.crypto)
    //Inyeccion de dependencias
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)
    //Detectar fugas de memoria en debug
    debugImplementation(libs.leakcanary.android)

    // RxJava 3
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
// Adaptador de Retrofit para RxJava 3 en Hilt
    implementation(libs.adapter.rxjava3)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

}