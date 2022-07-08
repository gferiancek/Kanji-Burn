apply {
    from("$rootDir/android-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.reviewDomain))
    "implementation"(project(Modules.reviewUseCases))

    "implementation"(Accompanist.pager)

    "implementation"(Coil.coil)
}