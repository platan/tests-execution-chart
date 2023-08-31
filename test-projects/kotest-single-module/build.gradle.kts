plugins {
    kotlin("jvm") version "1.7.10"
    id("io.github.platan.tests-execution-chart")
}

apply(from = "../checks.gradle")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
    testImplementation("io.kotest:kotest-assertions-core:5.4.2")
    testImplementation("io.kotest:kotest-framework-datatest:5.4.2")
}

tasks.test {
    useJUnitPlatform()
}


configure<io.github.platan.tests_execution_chart.gradle.CreateTestsExecutionReportExtension> {
    formats {
        html {
            script {
                src.set("https://cdn.jsdelivr.net/npm/mermaid@8.13.3/dist/mermaid.js")
            }
        }
        json {
            enabled.set(true)
        }
        mermaid {
            enabled.set(true)
        }
    }
}
