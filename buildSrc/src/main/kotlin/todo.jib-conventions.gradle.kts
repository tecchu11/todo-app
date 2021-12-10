plugins {
    id("com.google.cloud.tools.jib")
}

jib {
    from.platforms {
        platform {
            architecture = "arm64"
            os = "linux"
        }
    }
    to.image = "ghcr.io/tecchu11/todo-${project.name}"
    container.creationTime = "USE_CURRENT_TIMESTAMP"
}
