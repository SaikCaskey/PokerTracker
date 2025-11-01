@file:Suppress("UnstableApiUsage")

include(":app")
include(":libs:database")
include(":libs:domain")
include(":libs:ui-charts")
include(":libs:ui-compose")
include(":feature:dashboard")
include(":feature:planner")
include(":feature:stats")
include(":feature:account")

pluginManagement {
    repositories {
        includeBuild("build-logic")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PokerTracker"
