plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    `java-gradle-plugin`
    `maven-publish`
    groovy
    id("com.gradle.plugin-publish") version "1.1.0"
    id("com.diffplug.spotless") version "6.11.0"
    id("com.github.jakemarsden.git-hooks") version "0.0.2"
    id("io.github.platan.tests-execution-chart") version "0.2.0"
}

group = "io.github.platan"
version = "0.2.1-SNAPSHOT"

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
            id = "io.github.platan.tests-execution-chart"
            displayName = "Visualise tests execution schedule"
            description = "Visualise tests execution schedule"
            implementationClass = "io.github.platan.tests_execution_chart.TestsExecutionReportPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/platan/tests-execution-chart"
    vcsUrl = "https://github.com/platan/tests-execution-chart.git"
    description = "Visualise tests execution schedule"
    tags = listOf("test", "tests", "execution", "schedule", "report", "gantt", "chart", "parallel")
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
    "functionalTestImplementation"("org.spockframework:spock-core:2.3-groovy-3.0") {
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

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        // New versions of ktlint display:
        // "Property 'disabled_rules' is deprecated: Rename property 'disabled_rules' to 'ktlint_disabled_rules' in all '.editorconfig' files."
        // but .editorconfig file is ignored (Similar issue: https://github.com/pinterest/ktlint/issues/1599 )
        ktlint("0.46.0").editorConfigOverride(
            mapOf(
                "disabled_rules" to "package-name"
            )
        )
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint().editorConfigOverride(
            mapOf("indent_size" to "4")
        )
    }
}

gitHooks {
    setHooks(mapOf("pre-commit" to "spotlessCheck"))
}
