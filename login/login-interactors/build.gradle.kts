apply {
    from("$rootDir/kotlin-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.loginData))

    "implementation"(SqlDelight.runtime)
    "implementation"(KotlinX.coroutinesCore)
}