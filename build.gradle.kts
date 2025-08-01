plugins {
    kotlin("jvm") version "2.2.0"
}

group = "io.github.stream29"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.aallam.openai:openai-client:4.0.1")
    implementation("io.ktor:ktor-client-cio:3.2.0")
    implementation("io.github.stream29:json-schema-generator:1.1.0")
    testImplementation(kotlin("test"))
}
kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}