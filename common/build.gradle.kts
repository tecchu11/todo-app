plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    implementation(libs.logstash.logback.encoder)

    testImplementation(libs.spring.mockk)
}
