plugins {
    id("todo.spring-dependency-conventions")
    id("todo.spring-boot-conventions")
    id("todo.jib-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
