import pl.allegro.tech.build.axion.release.domain.hooks.HookContext

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    `java-gradle-plugin`
    `maven-publish`
    groovy
    id("com.gradle.plugin-publish") version "1.3.0"
    id("com.diffplug.spotless") version "7.0.2"
    id("com.github.jakemarsden.git-hooks") version "0.0.2"
    id("io.github.platan.tests-execution-chart") version "0.6.1"
    kotlin("plugin.serialization") version "2.1.0"
    id("pl.allegro.tech.build.axion-release") version "1.18.2"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
    id("org.gradle.wrapper-upgrade") version "0.12"
}

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

allprojects {
    apply(plugin = "groovy")

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    group = "io.github.platan"
    project.version = rootProject.scmVersion.version
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
    implementation(project(":tests-execution-chart-commons"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(gradleApi())
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    testImplementation(platform("org.codehaus.groovy:groovy-bom:3.0.22"))
    testImplementation("org.codehaus.groovy:groovy")
    testImplementation(platform("org.spockframework:spock-bom:2.4-M2-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")
}

gradlePlugin {
    website = "https://github.com/platan/tests-execution-chart"
    vcsUrl = "https://github.com/platan/tests-execution-chart.git"
    plugins {
        create("testsExecutionReportPlugin") {
            id = "io.github.platan.tests-execution-chart"
            displayName = "Visualise tests execution schedule"
            description = "Visualise tests execution schedule"
            implementationClass = "io.github.platan.tests_execution_chart.gradle.TestsExecutionReportPlugin"
            tags = listOf("test", "tests", "execution", "schedule", "report", "gantt", "chart", "parallel")
        }
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
    functionalTestImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    functionalTestImplementation("com.github.ajalt.clikt:clikt:5.0.0")
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

nexusPublishing {
    repositories {
        create("sonatype") {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint("0.50.0")
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

wrapperUpgrade {
    gradle {
        register("tests-execution-chart") {
            repo.set("platan/tests-execution-chart")
            baseBranch.set("main")
        }
    }
}
