plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "anna.camila.madu.simon.shelfshare"
    compileSdk = 34

    defaultConfig {
        applicationId = "anna.camila.madu.simon.shelfshare"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview.v7)
    implementation(libs.lifecycle.viewmodel.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
android {
    // Outras configurações...

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")  // Exclui licenças duplicadas
            excludes.add("META-INF/androidx.cardview_cardview.version")  // Exclui o arquivo duplicado específico
        }
    }
}

