plugins {
    id("todo.kotlin-jvm-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}

tasks.whenTaskAdded {
    if (this.name.startsWith("boot")) {
        this.enabled = false
    }
}
