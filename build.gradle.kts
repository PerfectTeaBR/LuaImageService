plugins {
    kotlin("jvm") version "1.9.22" 
}

group = "net.perfect.tea.luaimageservice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.perfecttea.powered.net")
}

dependencies {
    val ktor_version = "2.3.7"

    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Usamos a 3.0.5 para o import "mu.KotlinLogging" funcionar
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("io.ktor:ktor-server-content-negotiation:${ktor_version}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")

    implementation("ch.qos.logback:logback-classic:1.4.14")

}


kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
