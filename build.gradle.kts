plugins {
    kotlin("multiplatform") version "1.3.60"
}

group = "dk.rheasoft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted.
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm() // Create a JVM target with the default name 'jvm'
    js{
        browser {
            testTask {
                useKarma {
                    usePhantomJS()
                    useChromeHeadless()
//                    useChrome()
//                    useFirefox()
                }
            }
        }
    }
    // Create a JS target with a custom name 'nodeJs'


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
//                implementation(kotlin("test-karma"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("com.eclipsesource.minimal-json:minimal-json:0.9.5")
                implementation("com.github.salomonbrys.kotson:kotson:2.5.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

    }
}