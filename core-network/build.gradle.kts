apply {
    from("$rootDir/kotlin-library-build.gradle")
}

plugins {
    kotlin(Kotlin.serialization) version Kotlin.version
}


dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.coreCache))

    "implementation"("com.google.code.gson:gson:2.9.0")
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
}