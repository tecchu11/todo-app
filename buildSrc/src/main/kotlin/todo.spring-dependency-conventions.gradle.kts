plugins {
    id("todo.kotlin-jvm-conventions")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
