plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":common"))
    implementation(libs.bundles.rest.base)
    implementation(libs.spring.mybatis)
    implementation(libs.kulid)

    runtimeOnly(libs.mysql.java)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.spring.mybatis.test)
    testImplementation(libs.testcontainers.mysql)
}
