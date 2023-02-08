plugins {
    id("base.spring-dependency")
}

dependencies {
    api(libs.spring.logging)
    api(libs.logstash.logback.encoder)
}
