import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("base.maven")
    id("base.kotlin-analysis")
    kotlin("jvm")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
