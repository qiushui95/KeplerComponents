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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":update:presentation"))
    implementation(project(":update:entity"))
    implementation(project(":update:model"))

    implementation(AndroidDependency.Architecture.Starter.fullGradle)
    implementation(AndroidDependency.Architecture.Initializer.fullGradle)
    implementation(AndroidDependency.Architecture.Entity.fullGradle)
    implementation(AndroidDependency.Architecture.Getter.fullGradle)
    implementation(AndroidDependency.Architecture.Ext.fullGradle)
    implementation(AndroidDependency.Architecture.Http.fullGradle)
    implementation(AndroidDependency.Architecture.Error.fullGradle)

    implementation(AndroidDependency.Single.Appcompat.fullGradle)
    implementation(AndroidDependency.View.Material.fullGradle)
    implementation(AndroidDependency.View.ConstraintLayout.fullGradle)

    implementation(AndroidDependency.Single.AndroidUtil.fullGradle)

    implementation(AndroidDependency.Lifecycle.Runtime.fullGradle)

    implementation(AndroidDependency.BasePopup.Candy.fullGradle)

    implementation(AndroidDependency.Koin.AndroidExt.fullGradle)

    testImplementation(AndroidDependency.Test.Junit.fullGradle)

    androidTestImplementation(AndroidDependency.Test.JunitExt.fullGradle)
    androidTestImplementation(AndroidDependency.Test.Espresso.fullGradle)

    implementation(AndroidDependency.Coroutines.Core.fullGradle)

    implementation(AndroidDependency.Chucker.Debug.fullGradle)

    implementation(AndroidDependency.OkHttp.Interceptor.fullGradle)

    implementation("com.squareup.moshi:moshi:1.12.0")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = (kotlinOptions.freeCompilerArgs + listOf(
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
    )).distinct()
}