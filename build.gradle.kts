buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.hiltAndroid)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.sqlDelightGradlePlugin)
        classpath("com.android.tools.build:gradle:7.2.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}