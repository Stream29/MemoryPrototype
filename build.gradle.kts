plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
}

group = "io.github.stream29"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:3.2.2")
    implementation("io.ktor:ktor-client-cio:3.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.2")
    implementation("io.ktor:ktor-client-logging:3.2.2")
    implementation("io.ktor:ktor-client-content-negotiation:3.2.2")

    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("io.ktor:ktor-server-core:3.2.2")
    implementation("io.ktor:ktor-server-cio:3.2.2")
    implementation("io.ktor:ktor-server-call-logging:3.2.2")
    implementation("io.ktor:ktor-server-content-negotiation:3.2.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    implementation("io.github.stream29:json-schema-generator:1.1.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}