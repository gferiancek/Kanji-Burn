apply {
    from("$rootDir/kotlin-library-build.gradle")
}

plugins {
    kotlin(Kotlin.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(project(Modules.subjectDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation"(Ktor.logback)
    "implementation"(Ktor.logging)

    "implementation"(SqlDelight.runtime)
    "implementation"("com.google.code.gson:gson:2.9.0")
}

sqldelight {
    database("SubjectDatabase") {
        packageName = "com.gavinferiancek.subject_datasource.cache"
    }
}