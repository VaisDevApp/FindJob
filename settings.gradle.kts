pluginManagement {
    repositories {
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

rootProject.name = "FindJob"
include(":app")
include(":data-vacancy")
include(":feature-search-ui")
include(":feature-favorite-ui")
include(":core-ui")
include(":core-di")
include(":feature-other-ui")
include(":feature-shared-detail-ui")
