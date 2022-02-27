apply {
    from("$rootDir/android-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.subjectDomain))
    "implementation"(project(Modules.subjectInteractors))

    "implementation"(Accompanist.pager)

    "implementation"(Coil.coil)
}