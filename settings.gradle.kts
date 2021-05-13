val useRemote by extra(false)

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        if (extra["useRemote"].toString() == "false") {
            mavenLocal()
        }
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "Kepler Components"

include(":app")

include(":update:presentation")
include(":update:domain")
include(":update:model")
include(":update:entity")
