apply {
    from("$rootDir/kotlin-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.subjectDataSource))
    "implementation"(project(Modules.subjectDomain))

    "implementation"(SqlDelight.runtime)
    "implementation"(KotlinX.coroutinesCore)
}