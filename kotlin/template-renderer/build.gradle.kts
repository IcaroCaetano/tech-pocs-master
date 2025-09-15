plugins {
    kotlin("jvm") version "2.1.10"
    application
}

group = "org.rbleggi"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.itextpdf:itext-core:9.1.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")

    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.mockk:mockk:1.13.17")
}

application {
    mainClass = "com/rbleggi/templaterenderer/MainKt"
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}