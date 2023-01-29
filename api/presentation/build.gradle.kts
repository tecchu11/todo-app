plugins {
    id("todo.spring-boot-application-conventions")
    id("todo.jib-conventions")
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":api:application"))
    implementation(project(":api:domain"))
    runtimeOnly(project(":api:infrastructure"))

    implementation(project(":api:libs:logging"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)
    implementation(libs.kulid)

    testImplementation(libs.bundles.test.base)
    testImplementation(libs.bundles.test.base)
}
