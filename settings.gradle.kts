rootProject.name = "todo"

val rootProjectPaths = listOf(
    "api",
    "libs",
)
val nestedProjectsPaths = listOf(
    "libs:logging",
)

include(listOf(rootProjectPaths, nestedProjectsPaths).flatten())

nestedProjectsPaths.forEach {
    findProject(it)?.name = it.substringAfter(":")
}
