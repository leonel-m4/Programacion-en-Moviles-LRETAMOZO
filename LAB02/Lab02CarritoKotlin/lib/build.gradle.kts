plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("application")
}

application {
    mainClass.set("com.retamozo.lab02carritokotlin.CarritoKt")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
