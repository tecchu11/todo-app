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
    val gitHash: String? by project
    to.image = "ghcr.io/tecchu11/todo-${project.name}:$gitHash"
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
}
