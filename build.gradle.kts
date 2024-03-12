import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

group = "com.amstee"
version = "1.0-SNAPSHOT"

val flinkVersion: String by project
val confluentVersion: String by project
val protoVersion: String by project

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven")
}

dependencies {
//    implementation(group="org.apache.flink", name="flink-formats", version=flinkVersion)
//    implementation(group="io.confluent", name="kafka-schema-registry-client", version=confluentVersion)
    implementation(group="org.apache.flink", name="flink-core", version=flinkVersion)
    implementation(group="org.apache.flink", name="flink-table-common", version=flinkVersion)
    implementation(group="io.confluent", name="kafka-protobuf-serializer", version=confluentVersion)
    implementation(group="com.google.protobuf", name="protobuf-java", version=protoVersion)

    // Tests
    testImplementation("org.testcontainers:testcontainers:1.19.7")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("proto-confluent.jar")

    minimize()

    dependencies {
        exclude(dependency("org.apache.flink:.*:.*"))
        exclude(dependency("org.slf4j:.*:.*"))
    }
    mergeServiceFiles()
}

tasks.test {
    useJUnitPlatform()
}