buildscript {
    val kotlin_version by extra("2.0.0-RC2")
    dependencies {
        classpath(libs.google.services)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

    }
    repositories {
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}