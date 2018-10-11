import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.0-rc-146"

    repositories {
        mavenCentral()
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

plugins {
    base
    java
}

val kotlinVersion: String by extra
val kotlinTestVersion = "3.1.10"
val coroutinesVersion = "0.30.2-eap13"

subprojects {
    apply {
        plugin<KotlinPluginWrapper>()
    }

    group = "com.laurencegarmstrong.kwamp"
    version = "1.0.0"

    repositories {
        mavenCentral()
        jcenter()
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    dependencies {
        implementation(kotlin("stdlib", kotlinVersion))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation(kotlin("reflect", kotlinVersion))

        testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
    }
}