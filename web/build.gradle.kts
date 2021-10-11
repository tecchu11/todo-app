plugins {
    id("todo.kotlin-conventions")
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    testImplementation("com.ninja-squad:springmockk:3.0.1")
}
