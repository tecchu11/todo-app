plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
