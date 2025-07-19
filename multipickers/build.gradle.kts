plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka") version "2.0.0"
    id("maven-publish")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "github.returdev.multipickers"
    compileSdk = 36

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

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.07.00"))
    implementation("androidx.compose.material3:material3")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.returdev"
                artifactId = "multipickers"
                version = "1.0.1"

                artifact(javadocJar.get())
            }
        }
    }
}
