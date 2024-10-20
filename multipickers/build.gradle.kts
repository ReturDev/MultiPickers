plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("maven-publish")
}

android {
    namespace = "github.returdev.multipickers"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation ("androidx.compose.material3:material3")

}

val sourcesJar by tasks.registering(Jar::class) {
    from(android.sourceSets["main"].java.srcDirs) // Incluye las carpetas de código fuente
    archiveClassifier.set("sources") // Establece el nombre del archivo como "sources"
}

afterEvaluate{
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.returdev"
                artifactId = "multipickers"
                version = "1.0.1"

                artifact(sourcesJar.get())

            }

        }
    }
}