plugins {
    `kotlin-dsl`
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.2.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
}
