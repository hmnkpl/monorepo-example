import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("plugin.spring") version "1.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.springframework.boot") version "2.7.6"
}
dependencies {
    val grpcSpringBootVersion: String by project

    val grpcKotlinVersion: String by project
    val grpcVersion: String by project
    val postgresClientVersion: String by project
    val exposedVersion: String by project
    val junitJupiterVersion: String by project
    val dbSetupKotlinVersion: String by project
    val kotestExtensionSpringVersion: String by project
    val kotestExtensionTestContainersVersion: String by project
    val kotestRunnerJunit5Version: String by project
    val jwtVersion: String by project

    implementation(project(":libs:generated:proto"))
    implementation(project(":libs:shared"))
    testImplementation(project(":libs:shared-test"))

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.lognet:grpc-spring-boot-starter:$grpcSpringBootVersion")

    implementation("com.auth0:java-jwt:$jwtVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("joda-time:joda-time:2.12.2")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.exposed:spring-transaction:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresClientVersion")
    testImplementation("com.ninja-squad:DbSetup-kotlin:$dbSetupKotlinVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestExtensionSpringVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:$kotestExtensionTestContainersVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestRunnerJunit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
}
