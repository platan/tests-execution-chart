import pl.allegro.tech.build.axion.release.domain.hooks.HookContext

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    `java-gradle-plugin`
    `maven-publish`
    groovy
    id("com.gradle.plugin-publish") version "1.2.0"
    id("com.diffplug.spotless") version "6.19.0"
    id("com.github.jakemarsden.git-hooks") version "0.0.2"
    id("io.github.platan.tests-execution-chart") version "0.4.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("pl.allegro.tech.build.axion-release") version "1.15.3"
    id("com.github.ben-manes.versions") version "0.47.0"
}

group = "io.github.platan"

scmVersion {
    tag {
        prefix.set("release-")
    }
    hooks {
        pre(
            "fileUpdate",
            mapOf(
                "file" to "README.md",
                "pattern" to KotlinClosure2<String, HookContext, String>({ previousVersion, _ -> "version \"$previousVersion\"" }),
                "replacement" to KotlinClosure2<String, HookContext, String>({ releaseVersion, _ -> "version \"$releaseVersion\"" }),
            ),
        )
    }
}

project.version = scmVersion.version

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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    testImplementation(platform("org.codehaus.groovy:groovy-bom:3.0.18"))
    testImplementation("org.codehaus.groovy:groovy")
    testImplementation(platform("org.spockframework:spock-bom:2.4-M1-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")
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

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
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
    functionalTestImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    functionalTestImplementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
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
        ktlint("0.47.0").editorConfigOverride(
            mapOf(
                "ktlint_disabled_rules" to "package-name",
            ),
        )
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint().editorConfigOverride(
            mapOf("indent_size" to "4"),
        )
    }
}

gitHooks {
    setHooks(mapOf("pre-commit" to "spotlessCheck"))
}

tasks.register<JavaExec>("createRegressionHtmlReport") {
    classpath = sourceSets["functionalTest"].runtimeClasspath
    mainClass.set("io.github.platan.tests_execution_chart.JsonToHtmlReportApp")
    setArgsString("--input ./src/functionalTest/resources/report-visual-regression.json --output-dir build")
}
