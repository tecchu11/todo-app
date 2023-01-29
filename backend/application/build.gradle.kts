plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(project(":backend:domain"))

    implementation(project(":backend:libs:logging"))
    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)

    testImplementation(libs.bundles.test.base)
}
