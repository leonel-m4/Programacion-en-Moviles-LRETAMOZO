plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.retamozo.centrotareas"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.retamozo.centrotareas"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
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

    // Dependencia indicada en el laboratorio. Las corrutinas y viewModelScope vienen con ella.
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Mejora: historial de resultados guardado con DataStore.
    implementation(libs.androidx.datastore.preferences)
}
