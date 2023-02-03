plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    implementation(project(":backend:domain"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)
    implementation(libs.spring.mybatis)

    kapt(libs.spring.config.processor)

    runtimeOnly(libs.mysql.java)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.spring.mybatis.test)
    testImplementation(libs.testcontainers.mysql)
}
