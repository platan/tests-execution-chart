<p align="center">
    <img src="src/etc/logo/logo.svg" height="140">
</p>

**Gradle plugin that visualises tests execution schedule**

[![build status](https://img.shields.io/github/actions/workflow/status/platan/tests-execution-chart/ci.yml?branch=main)](https://github.com/platan/tests-execution-chart/actions/workflows/ci.yml)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.platan.tests-execution-chart)](https://plugins.gradle.org/plugin/io.github.platan.tests-execution-chart)
[![This project is using Percy.io for visual regression testing.](https://img.shields.io/badge/visual%20testing-percy-9E66BF?logo=percy&logoColor=white)](https://percy.io/e8832041/tests-execution-chart)

# Usage

`build.gradle.kts`:

```kotlin
plugins {
    id("io.github.platan.tests-execution-chart") version "0.4.0"
}
```

`build.gradle`:

```gradle
plugins {
    id "io.github.platan.tests-execution-chart" version "0.4.0"
}
```

```sh
./gradlew test createTestsExecutionReport --rerun-tasks

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

# Reports

`createTestsExecutionReport` task creates reports in HTML, JSON and [Mermaid](https://mermaid-js.github.io/mermaid/#/) formats.

## HTML

Located in `build/reports/tests-execution/html/*.html`.

<picture>
      <source media="(prefers-color-scheme: dark)" srcset=".readme/example-html-report-dark.png">
      <img src=".readme/example-html-report.png">
</picture>

## JSON

Located in `build/reports/tests-execution/json/*.json`.

- `startTime` - in milliseconds since the epoch
- `endTime` - in milliseconds since the epoch

```json
{
  "results": [
    {
      "className": "Test1Spec",
      "durationMs": 120,
      "endTime": 1667936777974,
      "startTime": 1667936777854,
      "resultType": "SUCCESS",
      "testName": "test 2"
    },
    {
      "className": "Test1Spec",
      "durationMs": 213,
      "endTime": 1667936778067,
      "startTime": 1667936777854,
      "resultType": "SUCCESS",
      "testName": "test 1"
    }
  ]
}
```

## Mermaid

Located in `build/reports/tests-execution/mermaid/*.txt`.

```
gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test1Spec
test 2 - 120 ms :active, 2022-11-08T20:46:17.854+0100, 2022-11-08T20:46:17.974+0100
test 1 - 213 ms :active, 2022-11-08T20:46:17.854+0100, 2022-11-08T20:46:18.067+0100
```

rendered: ([info](https://github.blog/2022-02-14-include-diagrams-markdown-files-mermaid/)):

```mermaid
gantt
    dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
    axisFormat %H:%M:%S.%L
    section Test1Spec
        test 2 - 120 ms: active, 2022-11-08T20:46:17.854+0100, 2022-11-08T20:46:17.974+0100
        test 1 - 213 ms: active, 2022-11-08T20:46:17.854+0100, 2022-11-08T20:46:18.067+0100
```

# Configuration

Options:

| Key                                      | Type    | Description                                                        | Default                                                          |
|------------------------------------------|---------|--------------------------------------------------------------------|------------------------------------------------------------------|
| `formats.html.enabled`                   | boolean | Generate report in html format                                     | `true`                                                           |
| `formats.html.script.embed`              | boolean | If true mermaid source will be downloaded and used locally in html | `false`                                                          |
| `formats.html.script.src`                | url     | Url to mermaid which should be used to generate html report        | `https://cdn.jsdelivr.net/npm/mermaid@9.4.3/dist/mermaid.min.js` |
| `formats.html.script.config.maxTextSize` | int     | Limit on the size of text used to generate diagrams                | `50000`                                                          |
| `formats.json.enabled`                   | boolean | Generate report in json format                                     | `true`                                                           |
| `formats.mermaid.enabled`                | boolean | Generate report in mermaid text format                             | `true`                                                           |
| `components.suite.enabled`               | boolean | Add suites/classes to report                                       | `false`                                                          |
| `shiftTimestampsToStartOfDay`            | boolean | Adjust the earliest timestamp to the start of the day              | `false`                                                          |
| `marks.totalTimeOfAllTests.enabled`      | boolean | Enable mark showing total time of all tests                        | `false`                                                          |
| `marks.totalTimeOfAllTests.name`         | string  | Label used for mark                                                | `total time of all tests`                                        |

`build.gradle.kts`:

```kotlin
configure<io.github.platan.tests_execution_chart.CreateTestsExecutionReportExtension> {
    formats {
        html {
            enabled.set(true)
            script {
                embed.set(false)
                config {
                    maxTextSize.set(110000)
                }
                src.set("https://cdn.jsdelivr.net/npm/mermaid@9.4.3/dist/mermaid.min.js")
            }
        }
        json {
            enabled.set(true)
        }
        mermaid {
            enabled.set(true)
        }
    }
    components {
        suites {
            enabled.set(false)
        }
    }
    shiftTimestampsToStartOfDay.set(true)
    marks {
        totalTimeOfAllTests {
            enabled.set(true)
            name.set("total time of all tests")
        }
    }
}
```

`build.gradle`:

```gradle
createTestsExecutionReport {
    formats {
        html {
            enabled = true
            script {
                embed = false
                config {
                    maxTextSize = 110000
                }
                src = "https://cdn.jsdelivr.net/npm/mermaid@9.4.3/dist/mermaid.min.js"
            }
        }
        json {
            enabled = true
        }
        mermaid {
            enabled = true
        }
    }
    components {
        suites {
            enabled = false
        }
    }
    shiftTimestampsToStartOfDay = true
    marks {
        totalTimeOfAllTests {
            enabled = true
            name = 'total time of all tests'
        }
    }
}
```

# Troubleshooting

> I see `Maximum text size in diagram exceeded` in an HTML report instead of a chart.

Mermaid has limit of maximum allowed size of the text diagram. Default value of this size is 50000. This can be overridden using `formats.html.script.config.maxTextSize` option.

# Development

You can publish plugin locally:

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

`build.gradle` (replace `X.X.X-SNAPSHOT` with the value returned by `./gradlew cV -q -Prelease.quiet`):

```gradle
plugins {
    id 'io.github.platan.tests-execution-chart' version 'X.X.X-SNAPSHOT'
}
```

# Release

1. `./gradlew createRelease` (or `./gradlew createRelease -Prelease.versionIncrementer=incrementMinor`) - creates a new tag and updates examples in README (but does not commit changes in README)
2. `git push origin release-[X.X.X]`
3. Prepare `~/.gradle/gradle.properties` and run `./gradlew clean build publishPlugins`
4. Edit Changelog in README.md and `git commit -m "Document release [X.X.X]"`

# Motivation

[JUnit](https://junit.org/junit5/docs/5.9.0/user-guide/#writing-tests-parallel-execution) and [Spock](https://spockframework.org/spock/docs/2.3/parallel_execution.html) support parallel execution. Both frameworks allow configuring execution mode for classes/specifications and methods/features. JUnit/Spock documentation illustrates how this configuration affects the execution schedule of tests.

Unfortunately, I did not find existing tool which allows to visualise tests executions schedule for particular project. This Gradle plugin tries to address this need.

Gradle can generate reports in JUnit XML format. But such reports cannot be used to generate charts, because they do not have start timestamps for test methods, only for test classes (with seconds precision).

# Changelog

## Unreleased

## 0.4.0 (1 June 2023)

### Added

- A new option `marks.totalTimeOfAllTests` allows to add mark showing total time of all tests

### Changed

- Do not remove `#` character from names of tasks/sections

## 0.3.1 (29 March 2023)

### Added

- A new option `shiftTimestampsToStartOfDay` allows to adjust the earliest timestamp to the start of the day

## 0.3.0 (12 March 2023)

### Changed

- Use `dateFormat` compatible with Mermaid 9.4.2
- Link to Mermaid 9.4.3 instead of the latest version

## 0.2.1 (07 March 2023)

- (fix) Render chart in HTML report when the test name contains a backtick character

## 0.2.0 (02 February 2023)

- (feature) Allow to set `maxTextSize` config option in Mermaid script in HTML report

## 0.1.0 (09 November 2022)

- Add option to create a report that visualises the tests execution schedule

# License

This project is licensed under the [MIT license](LICENSE).
