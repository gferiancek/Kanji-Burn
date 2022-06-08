apply {
    from("$rootDir/kotlin-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.coreCache))
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.reviewDomain))
    "implementation"(project(Modules.coreNetwork))

    "implementation"("com.google.code.gson:gson:2.9.0")
    "implementation"(KotlinX.coroutinesCore)
    "implementation"(KotlinX.serialization)
}