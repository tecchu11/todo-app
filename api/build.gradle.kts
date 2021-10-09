plugins {
    id("todo.kotlin-conventions")
    id("todo.kotlin-spring-conventions")
    id("todo.jib-conventions")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")
    implementation("com.github.guepardoapps:kulid:2.0.0.0")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.0")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.0"))
    testImplementation("org.testcontainers:mysql")
}
