rootProject.name = "todo"

val rootProjectPaths = listOf(
    "api",
)
val nestedProjectsPaths = listOf(
    "api:presentation",
    "api:application",
    "api:domain",
    "api:infrastructure",
    "api:libs:logging",
)

include(listOf(rootProjectPaths, nestedProjectsPaths).flatten())

nestedProjectsPaths.forEach {
    findProject(it)?.name = it.substringAfter(":")
}
