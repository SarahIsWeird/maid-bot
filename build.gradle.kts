import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
}

group = "com.sarahisweird"
version = "1.4-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.23.5-SNAPSHOT")

    testImplementation(kotlin("test"))
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("${project.name}-fat")
    group = "build"

    manifest {
        attributes["Implementation-Title"] = "Maid Bot"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "com.sarahisweird.maidbot.MaidBotKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(configurations.runtimeClasspath.get()
        .map { if (it.isDirectory) it else zipTree(it) })

    with(tasks.jar.get() as CopySpec)
}

tasks {
    build {
        dependsOn(fatJar)
    }

    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
