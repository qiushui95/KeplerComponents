import son.ysy.useful.dependencies.AndroidDependency

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.kepler.components"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(AndroidDependency.Single.Appcompat.fullGradle)
    implementation(AndroidDependency.View.Material.fullGradle)
    implementation(AndroidDependency.View.ConstraintLayout.fullGradle)
    testImplementation(AndroidDependency.Test.Junit.fullGradle)

    androidTestImplementation(AndroidDependency.Test.JunitExt.fullGradle)
    androidTestImplementation(AndroidDependency.Test.Espresso.fullGradle)
}