apply {
    from("$rootDir/kotlin-library-build.gradle")
}

plugins {
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(project(Modules.subjectDomain))

    "implementation"(SqlDelight.runtime)
    "implementation"("com.google.code.gson:gson:2.9.0")
}

sqldelight {
    database("KanjiBurnDatabase") {
        packageName = "com.gavinferiancek.core_cache.cache"
        sourceFolders = listOf("sqldelight")
    }
}