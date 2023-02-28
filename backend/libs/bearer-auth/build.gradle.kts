plugins {
    id("base.spring-dependency")
}

dependencies {
    implementation(libs.spring.security)
    implementation(libs.spring.starter)
    implementation(libs.jwt.auth0)

    testImplementation(libs.bundles.test.base)
}
