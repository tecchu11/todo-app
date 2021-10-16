plugins {
    id("todo.kotlin-jvm-conventions")
    id("todo.spring-dependency-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    api("io.github.microutils:kotlin-logging-jvm:2.0.11")
}
