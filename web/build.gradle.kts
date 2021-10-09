plugins {
    id("todo.kotlin-spring-conventions")
    id("todo.jib-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
