plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.7"

	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"

	java
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
	maven("https://repo1.maven.org/maven2/")
}

val mockitoAgent by configurations.creating {
	isTransitive = false
}

val integrationTest by sourceSets.creating {
	java.srcDir("src/integrationTest/java")
	resources.srcDir("src/integrationTest/resources")

	compileClasspath += sourceSets.main.get().output +
			configurations.testRuntimeClasspath.get()

	runtimeClasspath += output + compileClasspath
}

configurations {
	val integrationTestImplementation by getting {
		extendsFrom(configurations.testImplementation.get())
	}

	val integrationTestRuntimeOnly by getting {
		extendsFrom(configurations.testRuntimeOnly.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.0")
	implementation("org.modelmapper:modelmapper:3.2.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.googlecode.libphonenumber:libphonenumber:8.13.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("com.h2database:h2")
	testAnnotationProcessor("org.projectlombok:lombok")
	add("integrationTestImplementation", "org.springframework.boot:spring-boot-starter-test")
	add("integrationTestRuntimeOnly", "org.junit.platform:junit-platform-launcher")

	mockitoAgent("net.bytebuddy:byte-buddy-agent:1.14.12")
}

tasks.register<Test>("integrationTest") {
	description = "Runs integration tests"
	group = "verification"

	testClassesDirs = integrationTest.output.classesDirs
	classpath = integrationTest.runtimeClasspath

	shouldRunAfter(tasks.test)

	useJUnitPlatform()

}

tasks.named<ProcessResources>("processIntegrationTestResources") {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("check") {
	dependsOn("integrationTest")
}

tasks.withType<Test> {
	useJUnitPlatform()

	doFirst {
		jvmArgs("-javaagent:${mockitoAgent.singleFile}")
	}
}
