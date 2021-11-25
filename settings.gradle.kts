@file:Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.repsy.io/mvn/droibit/public")
        maven(url = "https://repo.repsy.io/mvn/chrynan/public")
    }
}

include(
    ":android",
    ":shared"
)