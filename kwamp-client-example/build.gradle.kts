import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
}

application {
    mainClassName = "com.laurencegarmstrong.kwamp.client.example.App"
}

repositories {
    maven(url = "https://dl.bintray.com/kotlin/ktor")
}

val ktorVersion = "0.9.5-rc13"
val logbackVersion = "1.2.1"

dependencies {
    implementation(project(":kwamp-client"))

    implementation("io.ktor:ktor-client-websocket:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}