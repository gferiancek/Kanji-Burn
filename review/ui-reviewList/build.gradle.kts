import SqlDelight.plugin

apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.reviewDomain))
    "implementation"(project(Modules.reviewInteractors))

    "implementation"(Accompanist.pager)
    "implementation"(Accompanist.pagerIndicator)

    "implementation"(Compose.iconsExtended)

    "implementation"(Coil.coil)
}