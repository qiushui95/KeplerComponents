import son.ysy.useful.dependencies.AndroidDependency

plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(AndroidDependency.Architecture.Model.fullGradle)
    implementation(AndroidDependency.Architecture.Http.fullGradle)
    implementation(AndroidDependency.Architecture.Entity.fullGradle)

    implementation(AndroidDependency.MoShi.Core.fullGradle)
    kapt(AndroidDependency.MoShi.Compiler.fullGradle)

    compileOnly(AndroidDependency.Coroutines.Core.fullGradle)

}