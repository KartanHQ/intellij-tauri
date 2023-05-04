import org.jetbrains.changelog.Changelog

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.intellij") version "1.13.3"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "2.0.0"
}

group = "com.nekofar.milad"
version = "1.0.0-alpha.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.3")
    type.set("IU") // Target IDE Platform

    plugins.set(listOf("JavaScript"))
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set("1.0.0-alpha.0")
    groups.set(emptyList())
    repositoryUrl.set("https://github.com/KartanHQ/intellij-tauri")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        version.set("1.0.0-alpha.0")
        sinceBuild.set("213")
        untilBuild.set("231.*")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            file("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").let { org.jetbrains.changelog.markdownToHTML(it) }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider {
            with(changelog) {
                val changelogItem = getOrNull("1.0.0-alpha.0")
                    ?: runCatching { getLatest() }.getOrElse { getUnreleased() }
                renderItem(
                    changelogItem.withHeader(false),
                    Changelog.OutputType.HTML,
                )
            }
        })
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
