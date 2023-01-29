plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    api(libs.spring.logging)
    api(libs.logstash.logback.encoder)
}
