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
    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.mockk:mockk:1.13.17")
}

application {
    mainClass = "com/rbleggi/taxsystem/MainKt"
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}