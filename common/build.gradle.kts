plugins {
    id("todo.spring-dependency-conventions")
}

dependencies {
    kapt(libs.spring.config.processor)

    implementation(libs.bundles.rest.base)
    implementation(libs.bundles.libs.base)
    implementation(libs.logstash.logback.encoder)

    testImplementation(libs.bundles.test.base)
}
