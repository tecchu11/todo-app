plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":backend:application"))
    implementation(project(":backend:domain"))
    runtimeOnly(project(":backend:infrastructure"))

    implementation(project(":backend:libs:logging"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)
    implementation(libs.kulid)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.bundles.test.base)
}
