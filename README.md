tests-execution-chart
===

<p align="center">
    <img src="src/etc/logo/logo.svg" height="140">
</p>

**Gradle plugin that visualises tests execution schedule**

# Usage

> **Warning**
> This project is under development and has not yet been published to the Gradle Plugin Portal.

Since this project has not been published to the Gradle Plugin Portal you have to publish it locally to use it:

```sh
cd tests-execution-chart
./gradlew publish
```
Artifacts are published to `../local-plugin-repository` directory.

Configure your project:

`settings.gradle` (set proper path instead of `/path-to`):
```gradle
pluginManagement {
    repositories {
        maven {
            url '/path-to/local-plugin-repository'
        }
        gradlePluginPortal()
    }
}

```
`build.gradle`:
```gradle
plugins {
    id 'com.github.platan.tests-execution-chart' version '0.0.1-SNAPSHOT'
}
```

[//]: # (Gradle + Kotlin:)

[//]: # ()
[//]: # (```kotlin)

[//]: # (plugins {)

[//]: # (    id&#40;"com.github.platan.tests-execution-chart"&#41;)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (Gradle + Groovy:)

[//]: # ()
[//]: # (```gradle)

[//]: # (plugins {)

[//]: # (    id 'com.github.platan.tests-execution-chart')

[//]: # (})

[//]: # (```)

```sh
./gradlew test createTestsExecutionReport

#...

Tests execution schedule report saved to /my-project/build/reports/tests-execution/mermaid/test.txt file.
Tests execution schedule report saved to /my-project/build/reports/tests-execution/json/test.json file.
Tests execution schedule report saved to /my-project/build/reports/tests-execution/html/test.html file.
```

Example result (HTML report):

<p align="center">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset=".readme/example_dark.svg">
      <img src=".readme/example.svg">
    </picture>
</p>

# Configuration

Gradle + Kotlin:

```kotlin
configure<com.github.platan.tests_execution_chart.CreateTestsExecutionReportExtension> {
    formats {
        html {
            enabled.set(true)
            script {
                src.set("https://cdn.jsdelivr.net/npm/mermaid@8.13.3/dist/mermaid.js")
                embed.set(true)
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
```

Gradle + Groovy:

```gradle
createTestsExecutionReport {
    formats {
        json {
            enabled = false
        }
        html {
            enabled = true
            script {
                src = "https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"
                embed = true
            }
        }
        mermaid {
            enabled = false
        }
    }
}
```

# Changelog

## Unreleased

- ...

# License

This project is licensed under the [MIT license](LICENSE).
