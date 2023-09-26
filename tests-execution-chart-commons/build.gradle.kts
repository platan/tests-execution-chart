plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    `maven-publish`
    id("org.jetbrains.kotlinx.kover") version "0.7.3"
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation(localGroovy())
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    testImplementation(platform("org.codehaus.groovy:groovy-bom:3.0.19"))
    testImplementation("org.codehaus.groovy:groovy")
    testImplementation(platform("org.spockframework:spock-bom:2.4-M1-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/platan/tests-execution-chart")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
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
