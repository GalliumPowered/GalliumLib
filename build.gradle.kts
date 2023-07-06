plugins {
    java
}

group = "net.zenoc.gallium"
version = "1.1.0-beta.4"

repositories {
    mavenCentral()
    // mavenLocal()
    maven("https://libraries.minecraft.net")
    maven("https://repo.zenoc.net/repository")
}

dependencies {
    compileOnly("com.mojang:brigadier:1.0.18")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.2.0")
    implementation("net.kyori:event-method-asm:3.0.0")
    implementation("org.json:json:20230618")
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("com.google.inject:guice:7.0.0")
}