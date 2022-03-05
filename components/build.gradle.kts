apply {
    from("$rootDir/android-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(Compose.iconsExtended)

    "implementation"(Accompanist.pager)
    "implementation"(Accompanist.pagerIndicator)

    "implementation"(Coil.coil)
}