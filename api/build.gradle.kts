import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.1.2"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

jib {
    from.platforms {
        platform {
            architecture = "arm64"
            os = "linux"
        }
    }
    to.image = "tecchu11/todo-app"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.8")
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")
    implementation("com.github.guepardoapps:kulid:2.0.0.0")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.0")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.0"))
    testImplementation("org.testcontainers:mysql")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<BootJar>("bootJar") {
    launchScript()
}
