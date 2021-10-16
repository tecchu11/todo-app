plugins {
    id("todo.spring-dependency-conventions")
    id("org.springframework.boot")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
