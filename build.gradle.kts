//import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.freefair.lombok") version "6.6"
    //id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}


group = "ac.grim.grimac"
version = "2.3.48"
description = "Libre simulation anticheat designed for 1.20 with 1.8-1.20 support, powered by PacketEvents 2.0."
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

val localEnvFile = File(
        "${System.getProperties().getProperty("user.home")}${File.separator}.gradle",
        "env-timolia.local.gradle.kts"
)

// Check if local env file exists
if (localEnvFile.exists()) {
    // Set project extras to local env file
    apply(from = localEnvFile.path)
} else {
    project.extra.set("mavenToken", System.getenv("MAVEN_LIVE_TOKEN") as String)
    project.extra.set("mavenUser", System.getenv("MAVEN_LIVE_USER") as String)
    project.extra.set("repository", System.getenv("MAVEN_LIVE_REPO") as String)
    project.extra.set("mavenUrl", System.getenv("MAVEN_LIVE_URL") as String)
}

repositories {
    mavenCentral()
    maven {
        url = uri("${project.extra["mavenUrl"] as String}/${project.extra["repository"] as String}")
        name = "Reposilite-" + project.extra["mavenUser"] as String

        authentication {
            create<BasicAuthentication>("basic")
        }
        credentials {
            username = project.extra["mavenUser"] as String
            password = project.extra["mavenToken"] as String
        }
    }
}

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
    maven("https://jitpack.io/") // Grim API
    maven("https://repo.viaversion.com") // ViaVersion
    maven("https://repo.aikar.co/content/groups/aikar/") // ACF
    maven("https://nexus.scarsz.me/content/repositories/releases") // Configuralize
    maven("https://repo.opencollab.dev/maven-snapshots/") // Floodgate
    maven("https://repo.codemc.io/repository/maven-snapshots/") // PacketEvents
    mavenCentral()
    // FastUtil, Discord-Webhooks
}

dependencies {
    implementation("com.github.retrooper.packetevents:spigot:2.1.0-SNAPSHOT")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    implementation("timolia.core:client-redis:git-master")
    implementation("timolia.integrations:discord:git-master")
    implementation("club.minnced:discord-webhooks:0.8.0")
    implementation("it.unimi.dsi:fastutil:8.5.9")
    implementation("org.jetbrains:annotations:23.1.0") // Why is this needed to compile?
    implementation("github.scarsz:configuralize:1.4.0")

    implementation("com.github.grimanticheat:grimapi:c3a80bdea5")
    // Used for local testing: implementation("ac.grim.grimac:grimapi:1.0")

    compileOnly("org.geysermc.floodgate:api:2.0-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.viaversion:viaversion-api:4.1.1")
    compileOnly("io.netty:netty-all:4.1.85.Final")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

publishing.publications.create<MavenPublication>("maven") {
    artifact(tasks["shadowJar"])
}

tasks.shadowJar {
    minimize()
    archiveFileName.set("${project.name}-${project.version}.jar")
    relocate("io.github.retrooper.packetevents", "ac.grim.grimac.shaded.io.github.retrooper.packetevents")
    relocate("com.github.retrooper.packetevents", "ac.grim.grimac.shaded.com.github.retrooper.packetevents")
    relocate("co.aikar.acf", "ac.grim.grimac.shaded.acf")
    relocate("club.minnced", "ac.grim.grimac.shaded.discord-webhooks")
    relocate("github.scarsz.configuralize", "ac.grim.grimac.shaded.configuralize")
    relocate("com.github.puregero", "ac.grim.grimac.shaded.com.github.puregero")
    relocate("com.google.gson", "ac.grim.grimac.shaded.gson")
    relocate("alexh", "ac.grim.grimac.shaded.maps")
    relocate("it.unimi.dsi.fastutil", "ac.grim.grimac.shaded.fastutil")
    relocate("net.kyori", "ac.grim.grimac.shaded.kyori")
    relocate("okhttp3", "ac.grim.grimac.shaded.okhttp3")
    relocate("okio", "ac.grim.grimac.shaded.okio")
    relocate("org.yaml.snakeyaml", "ac.grim.grimac.shaded.snakeyaml")
    relocate("org.json", "ac.grim.grimac.shaded.json")
    relocate("org.intellij", "ac.grim.grimac.shaded.intellij")
    relocate("org.jetbrains", "ac.grim.grimac.shaded.jetbrains")
}
