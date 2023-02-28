plugins {
    id("base.spring-dependency")
}

dependencies {
    implementation(project(":backend:domain"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.jackson.base)
    implementation(libs.spring.mybatis)
    implementation(libs.spring.starter)

    kapt(libs.spring.config.processor)

    runtimeOnly(libs.mysql.java)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.spring.mybatis.test)
    testImplementation(libs.testcontainers.mysql)
}
