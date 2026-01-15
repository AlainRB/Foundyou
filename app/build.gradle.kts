plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs)
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
            isMinifyEnabled = true
            isShrinkResources = true
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
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.swiperefreshlayout)

    // ----------- Inyección de Dependencias (Hilt) -----------
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)

    // ----------- Red (Retrofit, RxJava Adapter) -----------
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.adapter.rxjava3)

    // ----------- Programación Asíncrona (RxJava) -----------
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // ----------- Base de Datos (Room) -----------
    implementation(libs.room.runtime)
    implementation(libs.androidx.room.rxjava3)
    annotationProcessor(libs.room.compiler)

    // ----------- Utilidades (Imágenes, QR, Seguridad) -----------
    implementation(libs.glide)
    implementation(libs.core)
    implementation(libs.security.crypto)

    // ----------- Pruebas Unitarias (locales, en la JVM) -----------
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)

    // ----------- Pruebas de Instrumentación (en dispositivo/emulador) -----------
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ----------- Dependencias solo para Debug -----------
    debugImplementation(libs.leakcanary.android)

}