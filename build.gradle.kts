// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
        classpath(ClassPathPlugins.hilt)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven("https://jitpack.io")
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}