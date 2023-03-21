@file:JvmName("CreateRegressionHtmlReportApp")

package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.config.DEFAULT_MERMAID_SCRIPT_SRC
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.config.HtmlConfig
import io.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.FileInputStream

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    val config = HtmlConfig(
        HtmlConfig.Format(true, "html-report"),
        HtmlConfig.Script(
            DEFAULT_MERMAID_SCRIPT_SRC,
            false,
            HtmlConfig.Script.Options(50000)
        )
    )
    val logger = object : Logger {
        override fun lifecycle(message: String) {
            println(message)
        }
    }

    val report =
        json.decodeFromStream<TestExecutionScheduleReport>(FileInputStream("./src/functionalTest/resources/report-visual-regression.json"))

    HtmlGanttDiagramReporter(config, logger).report(report, File("build"), "example-task")
}
