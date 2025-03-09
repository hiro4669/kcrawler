plugins {
    kotlin("jvm") version "2.0.21"
}

group = "fd"
val version = "0.5.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.mohamedrejeb.ksoup:ksoup-html:$version")
}

tasks.test {
    useJUnitPlatform()
}