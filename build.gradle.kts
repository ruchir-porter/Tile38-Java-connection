plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.lettuce:lettuce-core:5.1.0.M1")
    // implementation ("io.lettuce:lettuce-core:6.3.1.RELEASE")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    // testImplementation("ch.qos.logback:logback-classic:0.9.26")
}

tasks.test {
    useJUnitPlatform()
}