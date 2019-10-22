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
    compile("org.apache.logging.log4j","log4j-slf4j-impl", "2.12.1")
    compile("org.apache.logging.log4j","log4j-api","2.12.1")
    compile("org.apache.logging.log4j","log4j-core","2.12.1")
    compile("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", "2.9.8")
    compile("com.fasterxml.jackson.core", "jackson-databind", "2.9.8")

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