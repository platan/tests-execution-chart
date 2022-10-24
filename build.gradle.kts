plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    `java-gradle-plugin`
    `maven-publish`
    groovy
    id("com.gradle.plugin-publish") version "1.0.0"
}

group = "com.github.platan"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val functionalTest = sourceSets.create("functionalTest") {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

val functionalTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

configurations["functionalTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(gradleApi())
    implementation("org.apache.commons:commons-text:1.10.0")
}

gradlePlugin {
    plugins {
        create("testsExecutionReportPlugin") {
            id = "com.github.platan.tests-execution-chart"
            displayName = "tests-execution-chart"
            description = "visualise tests execution schedule"
            implementationClass = "com.github.platan.tests_execution_chart.TestsExecutionReportPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/platan/tests-execution-chart"
    vcsUrl = "https://github.com/platan/tests-execution-chart.git"
    tags = listOf("tests", "execution", "schedule", "report", "gantt", "chart")
}

val functionalTestTask = tasks.register<Test>("functionalTest") {
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    useJUnitPlatform()
}

tasks.check {
    dependsOn(functionalTestTask)
}

gradlePlugin {
    testSourceSets(functionalTest)
}

dependencies {
    "functionalTestImplementation"("org.spockframework:spock-core:2.1-groovy-3.0") {
        exclude(group = "org.codehaus.groovy")
    }
}

publishing {
    publications {
        create<MavenPublication>("plugin") {
            groupId = groupId
            artifactId = artifactId
            version = version
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}
