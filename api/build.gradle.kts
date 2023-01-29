plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":libs:logging"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)
    implementation(libs.spring.mybatis)
    implementation(libs.kulid)
    implementation(libs.logstash.logback.encoder)

    runtimeOnly(libs.mysql.java)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.spring.mybatis.test)
    testImplementation(libs.testcontainers.mysql)
}
