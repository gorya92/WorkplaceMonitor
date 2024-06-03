import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.application)
    alias(libs.plugins.libres)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    id("com.google.gms.google-services")
}

kotlin {
    targetHierarchy.default()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")


    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(libs.libres)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.koin)
                implementation(libs.composeImageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.insetsx)
                implementation(libs.ktor.core)
                implementation(libs.ktor.json)
                implementation(libs.ktor.logging)
                implementation ("io.ktor:ktor-client-json:2.0.0")
                implementation("com.russhwolf:multiplatform-settings:1.1.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
                implementation(libs.kotlinx.serialization.json)
                implementation("io.coil-kt:coil-compose:2.6.0")
                implementation("io.coil-kt:coil:2.6.0")
                api("io.github.qdsfdhvh:image-loader:1.8.1")
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.sqlDelight.extensions)
                implementation(compose.components.resources)

            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.content.negotiation.v200)
                implementation(libs.ktor.serialization.kotlinx.json.v200)
                implementation(libs.sqlDelight.driver.android)
                implementation(libs.koin.android)
                // Import the Firebase BoM
                implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.0.0"))
                implementation(libs.firebase.messaging)
                implementation("me.pushy:sdk:1.0.109")


                implementation("com.google.firebase:firebase-auth")
                implementation("com.google.firebase:firebase-firestore")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqlDelight.driver.sqlite)
            }
        }



        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.driver.native)
            }
        }
    }
}

android {
    namespace = "goryachkovskiy.danil.mtuci.kmm"
    compileSdk = 34

    defaultConfig {
        minSdk = 25
        targetSdk = 34

        applicationId = "goryachkovskiy.danil.mtuci.kmm.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/**"
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "goryachkovskiy.danil.mtuci.kmm.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}



libres {
    // https://github.com/Skeptick/libres#setup
}
tasks.getByPath("desktopProcessResources").dependsOn("libresGenerateResources")
tasks.getByPath("desktopSourcesJar").dependsOn("libresGenerateResources")
dependencies {
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.work.runtime.ktx)
}


sqldelight {
    databases {
        create("AppDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("goryachkovskiy.danil.mtuci.kmm.data_cache.sqldelight")
            sourceFolders.set(listOf("kotlin"))
        }
    }
}
