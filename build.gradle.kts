plugins {
    id ("com.github.johnrengelman.shadow") version "5.1.0"
    java
    application
}

group = "com.sbogutyn"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "com.sbogutyn.crawler.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.1.0")
    testCompile("org.junit.jupiter", "junit-jupiter-engine", "5.1.0")
    testCompile("org.assertj", "assertj-core","3.11.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_12
}

tasks.withType<Test> {
    useJUnitPlatform()
}