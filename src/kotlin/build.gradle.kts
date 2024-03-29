import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.ben-manes.versions") version "0.42.0"
}

val kotlinVersion: String by extra("1.7.21")
val jacksonKotlinVersion: String by extra("2.14.1")

val javaxAnnotationVersion: String by extra("1.3.2")

val grpcSpringBootVersion: String by extra("4.9.1")

val postgresClientVersion: String by extra("42.5.1")

val grpcVersion: String by extra("1.51.0")
val grpcKotlinVersion: String by extra("1.3.0")
val protobufVersion: String by extra("3.21.11")
val coroutinesVersion: String by extra("1.6.4")

val exposedVersion: String by extra("0.41.1")

val argon2Version: String by extra("2.11")
val jwtVersion: String by extra("4.2.1")

val testcontainersVersion: String by extra("1.17.6")
val junitJupiterVersion: String by extra("5.9.1")
val dbSetupKotlinVersion: String by extra("2.1.0")
val kotestExtensionSpringVersion: String by extra("1.1.2")
val kotestExtensionTestContainersVersion: String by extra("1.3.4")
val kotestRunnerJunit5Version: String by extra("5.5.4")
val grpcTestingVersion: String by extra("1.48.1")

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "com.pal2hmnk.example"
    version = "0.0.1"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
        }
    }
}
tasks.register("copyProto", Copy::class) {
    from("../../schemas/proto")
    into("libs/generated/proto/src/main/proto/services")
    mustRunAfter("cleanDirs")
}
tasks.register("copyGraphql", Copy::class) {
    from("../../schemas/graphql")
    into("libs/generated/graphql/src/main/resources/schema")
    mustRunAfter("cleanDirs")
}
tasks.register("cleanDirs", Delete::class) {
    delete("libs/generated/proto/src/main/proto/services")
    delete("libs/generated/graphql/src/main/resources/schema")
}
tasks.register("copySchemas") {
    dependsOn(tasks.named("copyProto"))
    dependsOn(tasks.named("copyGraphql"))
    dependsOn(tasks.named("cleanDirs"))
}
tasks.register("makeSchemas") {
    dependsOn(":libs:generated:graphql:generateJava")
    dependsOn(":libs:generated:proto:generateProto")
    mustRunAfter("copySchemas")
}
tasks.register("withSchemaChange") {
    dependsOn(tasks.named("copySchemas"))
    dependsOn(tasks.named("makeSchemas"))
}
