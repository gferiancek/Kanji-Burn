apply {
    from("$rootDir/kotlin-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.subjectDataSource))
    "implementation"(project(Modules.subjectDomain))

    "implementation"(KotlinX.coroutinesCore)
}