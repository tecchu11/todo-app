plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    implementation(libs.spring.starter)
    implementation(libs.spring.security)
    implementation(libs.jwt.auth0)

    testImplementation(libs.bundles.test.base)
}
