apply {
    from("$rootDir/android-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.core))
    "implementation"(Compose.iconsExtended)

    "implementation"(Accompanist.pager)
    "implementation"(Accompanist.pagerIndicator)
}