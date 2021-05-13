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

setProperty("archivesBaseName", "kepler_update_entity")


dependencies {
    implementation(AndroidDependency.Architecture.Model.fullGradle)
    implementation(AndroidDependency.Architecture.Http.fullGradle)
    implementation(AndroidDependency.Architecture.Entity.fullGradle)

    implementation(AndroidDependency.MoShi.Core.fullGradle)
    kapt(AndroidDependency.MoShi.Compiler.fullGradle)

    implementation(AndroidDependency.Coroutines.Core.fullGradle)

}