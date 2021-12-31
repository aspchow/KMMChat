plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("com.rickclephas.kmp.nativecoroutines")
}

version = "1.0"

kotlin {
    android()
    iosX64{
        binaries {
            findTest(org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG)?.linkerOpts("-lsqlite3")
        }
    }
    iosArm64 {
        binaries {
            findTest(org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG)?.linkerOpts("-lsqlite3")
        }
    }
    //iosSimulatorArm64() sure all ios dependencies support this target

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlinx.coroutinesCore) {
                    isForce = true
                }

                with(Deps.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(clientSerialization)
                }

                with(Deps.Kotlinx) {
                    implementation(serializationCore)
                }

                with(Deps.SqlDelight) {
                    implementation(runtime)
                    implementation(coroutineExtensions)
                }

                with(Deps.Log) {
                    api(slf4j)
                }

                with(Deps.Koin) {
                    api(core)
                }

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientAndroid)
                implementation(Deps.SqlDelight.androidDriver)
                implementation(Deps.Android.okhttp)

            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting {
            dependencies {
                implementation(Deps.Ktor.clientIos)
                implementation(Deps.SqlDelight.nativeDriver)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation(Deps.Ktor.clientIos)
                implementation(Deps.SqlDelight.nativeDriver)
            }
        }
        //val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation(Deps.Ktor.clientIos)
                implementation(Deps.SqlDelight.nativeDriver)
            }
            //iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        //val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
        }
    }
    sqldelight {
        database("AppDatabase") {
            packageName = "com.avinash.database.db"
            sourceFolders = listOf("sqldelight")
            linkSqlite = true
        }
    }
}



android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}