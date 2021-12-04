plugins {
    id("todo.spring-dependency-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.logstash.logback:logstash-logback-encoder:7.0.1")
}
