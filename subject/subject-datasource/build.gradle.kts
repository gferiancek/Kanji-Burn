apply {
    from("$rootDir/kotlin-library-build.gradle")
}

plugins {
    kotlin(Kotlin.serialization) version Kotlin.version
}

dependencies {
    "implementation"(project(Modules.subjectDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation"(Ktor.logback)
    "implementation"(Ktor.logging)
}