pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy{
        eachPlugin{
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.2.2")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.7.20")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "ULife"

include(":app")
include(":uicommon")
include(":ktor-sample")
