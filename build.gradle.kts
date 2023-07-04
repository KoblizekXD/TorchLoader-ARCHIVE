plugins {
    kotlin("jvm") version "1.8.0"
    id("maven-publish")
    id("java-gradle-plugin")
}

group = "io.github.koblizekxd"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation(gradleApi())
    // implementation("torch-loader:torch-loader.gradle.plugin:1.0")

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
            groupId = "io.github.koblizekxd"
            artifactId = "torch-loader"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}