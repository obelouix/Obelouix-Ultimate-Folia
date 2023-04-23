import xyz.jpenilla.runpaper.task.RunServer
import xyz.jpenilla.runtask.service.DownloadsAPIService

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.4"
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
}

group = "fr.obelouix"
version = "1.0-SNAPSHOT"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    listOf(
            "https://repo.papermc.io/repository/maven-public/",              // Paper
            "https://repo.papermc.io/repository/maven-snapshots/",            // Paper snapshots (fpr Folia)
            "https://oss.sonatype.org/content/repositories/snapshots/",      // Sonatype
            "https://repo.opencollab.dev/maven-snapshots",                  // Floodgate
            "https://repo.opencollab.dev/maven-releases"                   // Cumulus & GeyserMC
    ).forEach {
        maven(it)
    }
}

dependencies {
    //paperweightDevBundle("1.19.4-R0.1-SNAPSHOT")
    paperweight.foliaDevBundle("1.19.4-R0.1-SNAPSHOT")

    // No need to add configurate-core, it's already included in paper server
    implementation(libs.configurate.hocon) // Configurate
    implementation(libs.paperlib) // PaperLib
    implementation(libs.bundles.cloudBundle) // Cloud
    compileOnly(libs.bundles.geyserBundle) // GeyserMC & Floodgate
    implementation(libs.bom.intellectualsites) // FAWE
    implementation(libs.bundles.faweBundle) // FAWE
}


tasks {

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything

    }

    assemble {
        dependsOn("reobfJar")
    }

    // Task to run a non mojang mapped Folia server
    register<RunServer>("runFoliaServer") {

        group = "run paper"

        downloadsApiService.convention(DownloadsAPIService.registerIfAbsent(project) {
            downloadsEndpoint = "https://papermc.io/api/v2/"
            downloadProjectName = "folia"
            buildServiceName = "folia-download-service"
        })
        minecraftVersion("1.19.4")
        dependsOn("assemble")
        //pluginJars(*rootProject.getTasksByName("assemble", false).map { (it as Jar).archiveFile }.toTypedArray())
        pluginJars(file("build/libs/ObelouixUltimate-1.0-SNAPSHOT.jar"))
        //pluginJars(*rootProject.getTasksByName("jar", false).map { (it as Jar).archiveFile }.toTypedArray())

    }

}