plugins {
    id("todo.kotlin-spring-conventions")
    id("todo.jib-conventions")
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
