plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    `maven-publish`
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
    signing
    id("io.github.platan.tests-execution-chart")
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.apache.commons:commons-text:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    testImplementation(platform("org.codehaus.groovy:groovy-bom:3.0.21"))
    testImplementation("org.codehaus.groovy:groovy")
    testImplementation(platform("org.spockframework:spock-bom:2.4-M4-groovy-4.0"))
    testImplementation("org.spockframework:spock-core")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("$groupId:$artifactId")
                description.set("Visualise tests execution schedule")
                url.set("https://github.com/platan/tests-execution-chart")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/platan/tests-execution-chart/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("platan")
                        name.set("Marcin Mielnicki")
                        email.set("projects@platan.space")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/platan/tests-execution-chart.git")
                    developerConnection.set("scm:git:ssh://github.com:platan/tests-execution-chart.git")
                    url.set("https://github.com/platan/tests-execution-chart/tree/main")
                }
            }
        }
    }
}

tasks.test {
    finalizedBy(tasks.koverHtmlReport, tasks.koverXmlReport)
}

tasks.koverXmlReport {
    dependsOn(tasks.test)
}
tasks.koverHtmlReport {
    dependsOn(tasks.test)
}

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

signing {
    setRequired({
        (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publishToSonatype")
    })
    sign(publishing.publications["mavenJava"])
}
