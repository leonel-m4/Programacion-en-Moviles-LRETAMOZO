import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val localProps = Properties().apply {
    val archivo = rootProject.file("local.properties")
    if (archivo.exists()) {
        archivo.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.retamozo.campusgps"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.retamozo.campusgps"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["MAPS_API_KEY"] =
            localProps.getProperty("MAPS_API_KEY", "").trim()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    // Las cuatro dependencias indicadas en el laboratorio.
    implementation(libs.play.services.location)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.maps.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Fase 2: último lugar visitado.
    implementation(libs.androidx.datastore.preferences)
}
