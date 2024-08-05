// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.2" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.firebase.crashlytics") version "3.0.1" apply false
    id("com.android.library") version "8.4.2" apply false

}


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the Google Services classpath
        classpath("com.google.gms:google-services:4.4.1")
    }
}


