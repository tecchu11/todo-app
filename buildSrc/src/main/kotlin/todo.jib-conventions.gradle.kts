plugins {
    id("com.google.cloud.tools.jib")
}

repositories {
    mavenCentral()
}

jib {
    from.platforms {
        platform {
            architecture = "arm64"
            os = "linux"
        }
    }
    to.image = "tecchu11/todo-${project.name}"
    container.creationTime = "USE_CURRENT_TIMESTAMP"
}
