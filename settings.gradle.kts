pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
    }
}

rootProject.name = "Weather Analyzer"
include(":app")
include(":domain")
include(":data")
