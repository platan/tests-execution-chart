@file:JvmName("JsonToHtmlReportApp")

package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.gradle.config.formats.DEFAULT_MERMAID_SCRIPT_SRC
import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.ReportCreator
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.html.HtmlConfig
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.FileInputStream

private val json = Json { ignoreUnknownKeys = true }

private val logger = object : Logger {
    override fun lifecycle(message: String) {
        println(message)
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun main(args: Array<String>) {
    val parser = ArgParser("jsonToHtml")
    val input by parser.option(ArgType.String, fullName = "input", description = "Input file in JSON format").required()
    val outputDir by parser.option(ArgType.String, fullName = "output-dir").required()
    val mermaidScriptSrc by parser.option(ArgType.String, fullName = "mermaid-script-src")
        .default(DEFAULT_MERMAID_SCRIPT_SRC)
    val embed by parser.option(ArgType.Boolean, fullName = "embed-mermaid-script").default(false)
    val maxTextSize by parser.option(ArgType.Int, fullName = "mermaid-max-text-size").default(50_000)
    val taskName by parser.option(ArgType.String, fullName = "task-name").default("test")

    parser.parse(args)

    val outputLocation = "html-report"
    val report = json.decodeFromStream<TestExecutionScheduleReport>(FileInputStream(input))
    val reportConfig = ReportConfig(
        listOf(
            HtmlConfig(
                true,
                outputLocation,
                HtmlConfig.Script(
                    mermaidScriptSrc,
                    embed,
                    HtmlConfig.Script.Options(maxTextSize)
                )
            ),
        ),
        ReportConfig.Marks(),
        false,
    )
    ReportCreator(logger).createReports(report, reportConfig, File(outputDir), taskName)
}
