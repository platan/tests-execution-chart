plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation(localGroovy())
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    testImplementation(platform("org.codehaus.groovy:groovy-bom:3.0.18"))
    testImplementation("org.codehaus.groovy:groovy")
    testImplementation(platform("org.spockframework:spock-bom:2.4-M1-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")
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
