apply {
    from("$rootDir/kotlin-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.reviewData))
    "implementation"(project(Modules.reviewDomain))
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.coreCache))
    "implementation"(project(Modules.coreNetwork))

    "implementation"(SqlDelight.runtime)
    "implementation"(KotlinX.coroutinesCore)
}