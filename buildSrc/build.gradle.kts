plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    val kotlin = "1.9.21"
    val detekt = "1.23.4"
    val springDependency = "1.1.4"
    val springBoot = "3.2.0"
    val jib = "3.4.0"

    // kotlin-conventions
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detekt")
    // spring-dependency-conventions
    implementation("io.spring.gradle:dependency-management-plugin:$springDependency")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlin")
    // spring-boot-conventions
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBoot")
    // google-container-tools/jib conventions
    implementation("com.google.cloud.tools:jib-gradle-plugin:$jib")
}
