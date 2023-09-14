import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("jacoco")


}

val lifecycle_version ="2.6.2"
jacoco {
    toolVersion = "0.8.8"
}




val jacocoTestReport = tasks.register("jacocoTestReport")

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")

    }
}



android {

    testCoverage {
        jacocoVersion ="0.8.8"
    }


    applicationVariants.all(closureOf<com.android.build.gradle.api.ApplicationVariant> {
        val testTaskName = "test${this@closureOf.name.capitalize()}UnitTest"

        val excludes = listOf(
            // Android
            "**/R.class",
            "**/R\$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*"
        )

        val reportTask = tasks.register("jacoco${testTaskName.capitalize()}Report", JacocoReport::class) {
            dependsOn(testTaskName)

            reports {

                xml.required.set(false)
                csv.required.set(false)
                html.required.set(true)
                html.outputLocation.set(layout.buildDirectory.dir("jacoco"))
            }

            classDirectories.setFrom(
                files(
                    fileTree(this@closureOf.javaCompileProvider.get().destinationDir) {
                        exclude(excludes)
                    },
                    fileTree("$buildDir/tmp/kotlin-classes/${this@closureOf.name}") {
                        exclude(excludes)
                    }
                )
            )

            // Code underneath /src/{variant}/kotlin will also be picked up here
            sourceDirectories.setFrom(this@closureOf.sourceSets.flatMap { it.javaDirectories })
            executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
        }

        jacocoTestReport.dependsOn(reportTask)
    })

    namespace = "com.abh.sample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.abh.sample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {

        animationsDisabled =  true

        unitTests.all {
            jacoco {

            }
        }

        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }





    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            testCoverage
        }

        getByName("debug") {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {





    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.0-alpha01")
    implementation ("androidx.test.espresso:espresso-intents:3.5.1")
    debugImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test:runner:1.5.0")
    androidTestImplementation ("androidx.test:rules:1.6.0-alpha01")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.6.0-alpha01")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")


    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")





// ViewModel
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
// ViewModel utilities for Compose
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
// LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation ("com.github.bumptech.glide:glide:4.13.2")


    implementation ("org.jacoco:org.jacoco.core:0.8.8")






}