buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.Gradle.kotlin)
        classpath(Deps.Gradle.android)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        val kmpNativeCoroutinesVersion = "0.8.0"
        classpath("com.rickclephas.kmp:kmp-nativecoroutines-gradle-plugin:$kmpNativeCoroutinesVersion")

        with(Deps.Gradle) {
            classpath(sqlDelight)
        }

    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}