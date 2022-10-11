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

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(gradleApi())
}

gradlePlugin {
    plugins {
        create("testsGanttChartPlugin") {
            id = "com.github.platan.tests-execution-chart"
            displayName = "tests-execution-chart"
            description = "Create a Gantt chart presenting tests execution"
            implementationClass = "com.github.platan.testsganttchart.TestsGanttChartPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/platan/tests-execution-chart"
    vcsUrl = "https://github.com/platan/tests-execution-chart.git"
    tags = listOf("tests", "execution", "gantt", "chart")
}

val functionalTest = sourceSets.create("functionalTest")
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
