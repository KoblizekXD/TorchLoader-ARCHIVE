import io.github.koblizekxd.torch.main.minecraft

plugins {
    kotlin("jvm") version "1.8.0"
    id("maven-publish")
    id("java-gradle-plugin")
    id("torch-loader") version "1.0"
}

group = "io.github.koblizekxd"
version = "1.0"

minecraft {
    mappings = "mcp-forge"
    version = "1.12.2"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(gradleApi())
    implementation("torch-loader:torch-loader.gradle.plugin:1.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
gradlePlugin {
    plugins {
        create("torch-loader") {
            id = "torch-loader"
            implementationClass = "io.github.koblizekxd.torch.TorchPlugin"
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}