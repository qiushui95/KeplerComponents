import son.ysy.useful.dependencies.AndroidDependency

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

setProperty("archivesBaseName", "kepler_update_presentation")

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets["main"].java.srcDirs("src/main/kotlin")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":update:entity"))
    implementation(project(":update:domain"))

    implementation(AndroidDependency.Architecture.Starter.fullGradle)
    implementation(AndroidDependency.Architecture.DomainImpl.fullGradle)
    implementation(AndroidDependency.Architecture.FlowWork.fullGradle)
    implementation(AndroidDependency.Architecture.Error.fullGradle)

    implementation(AndroidDependency.BasePopup.Candy.fullGradle)

    implementation(AndroidDependency.DownloadAndUpload.Core.fullGradle)

    implementation(AndroidDependency.Lifecycle.ViewModel.fullGradle)

    implementation(AndroidDependency.Fragment.Core.fullGradle)
    implementation(AndroidDependency.Single.Activity.fullGradle)

    implementation(AndroidDependency.Coroutines.Core.fullGradle)

    implementation(AndroidDependency.Koin.AndroidExt.fullGradle)

    implementation(AndroidDependency.Single.AndroidUtil.fullGradle)

    implementation(AndroidDependency.WorkManager.Core.fullGradle)
}