plugins {
    id("base.kotlin-jvm")
    id("io.spring.dependency-management")
    kotlin("kapt")
    kotlin("plugin.spring")

}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
