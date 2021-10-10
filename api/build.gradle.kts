plugins {
    id("todo.kotlin-spring-conventions")
    id("todo.jib-conventions")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")

    implementation("com.github.guepardoapps:kulid:2.0.0.0")
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.0")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.0"))
    testImplementation("org.testcontainers:mysql")
}
