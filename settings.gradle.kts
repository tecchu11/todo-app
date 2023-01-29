rootProject.name = "todo"

val rootProjectPaths = listOf(
    "backend",
)
val nestedProjectsPaths = listOf(
    "backend:api",
    "backend:application",
    "backend:domain",
    "backend:infrastructure",
    "backend:libs:logging",
)

include(listOf(rootProjectPaths, nestedProjectsPaths).flatten())

nestedProjectsPaths.forEach {
    findProject(it)?.name = it.substringAfter(":")
}
