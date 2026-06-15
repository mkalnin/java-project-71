plugins {
    id("java")
    id("com.github.ben-manes.versions") version "0.53.0"
    id("application")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    annotationProcessor("info.picocli:picocli:4.7.6")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("hexlet.code.App");
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "hexlet.code.App"
    }
}