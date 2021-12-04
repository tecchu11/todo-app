plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(project(":common"))
    implementation(libs.spring.mybatis)
    implementation(libs.kulid)

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation(libs.spring.mockk)
    testImplementation(libs.spring.mybatis.test)
    testImplementation(libs.testcontainers.mysql)
}
