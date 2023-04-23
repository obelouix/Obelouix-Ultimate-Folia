rootProject.name = "ObelouixUltimate"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-snapshots/")
    }
}

dependencyResolutionManagement {

    versionCatalogs {
        create("libs") {
            library("configurate-hocon", "org.spongepowered", "configurate-hocon").version("4.1.2")
            library("paperlib", "io.papermc", "paperlib").version("1.0.7")
            listOf("cloud-paper", "cloud-brigadier", "cloud-minecraft-extras").forEach {
                library(it, "cloud.commandframework", it).version("1.8.1")
            }

            library("geyser", "org.geysermc.geyser", "api").version("2.1.0-SNAPSHOT")
            library("floodgate", "org.geysermc.floodgate", "api").version("2.2.2-SNAPSHOT")

            library("bom-intellectualsites", "com.intellectualsites.bom", "bom-1.18.x").version("1.25")
            library("fawe-core", "com.fastasyncworldedit", "FastAsyncWorldEdit-Core").version("2.6.0")
            library("fawe-bukkit", "com.fastasyncworldedit", "FastAsyncWorldEdit-Bukkit").version("2.6.0")

            bundle("cloudBundle", listOf("cloud-paper", "cloud-brigadier", "cloud-minecraft-extras"))
            bundle("geyserBundle", listOf("geyser", "floodgate"))
            bundle("faweBundle", listOf("fawe-core", "fawe-bukkit"))
        }
    }
}