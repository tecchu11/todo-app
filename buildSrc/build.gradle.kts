plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    val kotlin = "1.7.10"
    val ktlint = "11.0.0"
    val detekt = "1.21.0"
    val springDependency = "1.0.13.RELEASE"
    val springBoot = "2.7.3"
    val jib = "3.2.1"

    // kotlin-conventions
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:$ktlint")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detekt")
    // spring-dependency-conventions
    implementation("io.spring.gradle:dependency-management-plugin:$springDependency")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlin")
    // spring-boot-conventions
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBoot")
    // google-container-tools/jib conventions
    implementation("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:$jib")
}
