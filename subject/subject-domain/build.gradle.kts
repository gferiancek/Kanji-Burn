apply {
    from("$rootDir/kotlin-library-build.gradle")
}


dependencies {
    "implementation"(project(Modules.core))
}