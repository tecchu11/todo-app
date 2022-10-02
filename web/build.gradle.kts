plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":common"))
    implementation(libs.bundles.rest.base)
    implementation(libs.jackson.datetype)
    implementation(libs.spring.thymeleaf)

    testImplementation(libs.bundles.test.base)
}
