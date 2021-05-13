import son.ysy.useful.dependencies.AndroidDependency

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

setProperty("archivesBaseName", "kepler_update_model")

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

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
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

    implementation(AndroidDependency.Architecture.Model.fullGradle)
    implementation(AndroidDependency.Architecture.Starter.fullGradle)
    implementation(AndroidDependency.Architecture.Http.fullGradle)
    implementation(AndroidDependency.Architecture.Entity.fullGradle)
    implementation(AndroidDependency.Architecture.Getter.fullGradle)

    implementation(AndroidDependency.MoShi.Core.fullGradle)

    kapt(AndroidDependency.MoShi.Compiler.fullGradle)

    implementation(AndroidDependency.Coroutines.Core.fullGradle)

    implementation(AndroidDependency.Koin.AndroidExt.fullGradle)

    implementation(AndroidDependency.Room.Ktx.fullGradle)
    kapt(AndroidDependency.Room.Compiler.fullGradle)
}