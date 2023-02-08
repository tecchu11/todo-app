plugins {
    id("base.spring-dependency")
}

dependencies {
    api(project(":backend:domain"))
    implementation(project(":backend:libs:bearer-auth"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.bundles.rest.base)

    kapt(libs.spring.config.processor)

    testImplementation(libs.bundles.test.base)
}
