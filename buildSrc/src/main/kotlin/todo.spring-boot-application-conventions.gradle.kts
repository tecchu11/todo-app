plugins {
    id("todo.spring-dependency-conventions")
    id("org.springframework.boot")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
