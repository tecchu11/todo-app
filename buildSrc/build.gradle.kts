plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // kotlin-conventions
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.2.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0")
    // spring-dependency-conventions
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.6.0")
    // spring-boot-conventions
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.1")
    // google-container-tools/jib conventions
    implementation("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:3.1.4")
}
