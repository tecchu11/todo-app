plugins {
    id("base.spring-boot-app")
    id("base.jib")
}

dependencies {
    implementation(project(":backend:application"))
    implementation(project(":backend:libs:bearer-auth"))
    implementation(project(":backend:libs:logging"))
    runtimeOnly(project(":backend:infrastructure"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.kotlin.jackson)
    implementation(libs.spring.actuator)
    implementation(libs.spring.openapi.ui)
    implementation(libs.spring.security)
    implementation(libs.spring.validation)
    implementation(libs.spring.web)
    implementation(libs.kulid)

    kapt(libs.spring.config.processor)

    testImplementation(libs.bundles.test.base)
}
