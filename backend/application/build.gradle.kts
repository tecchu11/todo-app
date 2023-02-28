plugins {
    id("base.spring-dependency")
}

dependencies {
    api(project(":backend:domain"))
    implementation(project(":backend:libs:bearer-auth"))

    implementation(libs.bundles.kotlin.base)
    implementation(libs.kotlin.jackson)
    implementation(libs.spring.security)
    implementation(libs.spring.starter)
    implementation(libs.kulid)

    kapt(libs.spring.config.processor)

    testImplementation(libs.bundles.test.base)
}
