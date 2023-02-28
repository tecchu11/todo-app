plugins {
    id("base.spring-dependency")
}

dependencies {
    implementation(libs.bundles.kotlin.base)
    implementation(libs.spring.starter)
    implementation(libs.kulid)

    kapt(libs.spring.config.processor)

    testImplementation(libs.bundles.test.base)
}
