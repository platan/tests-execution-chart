@file:JvmName("JsonToHtmlReportApp")

package io.github.platan.tests_execution_chart

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.int
import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.ReportCreator
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.html.HtmlConfig
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

private class CommandLineParser : CliktCommand() {
    val input by option(envvar = "input", help = "Input file in JSON format").required()
    val outputDir by option(envvar = "output-dir").required()
    val mermaidScriptSrc by option(envvar = "mermaid-script-src").default(HtmlConfig.Script.getSrcDefault())
    val embed by option(envvar = "embed-mermaid-script").boolean().default(false)
    val maxTextSize by option(envvar = "mermaid-max-text-size").int().default(50_000)
    val taskName by option(envvar = "task-name").default("test")

    override fun run() = Unit
}

@OptIn(ExperimentalSerializationApi::class)
fun main(args: Array<String>) {
    val commandLineParser = CommandLineParser()
    commandLineParser.main(args)

    val outputLocation = "html-report"
    val report = json.decodeFromStream<TestExecutionScheduleReport>(FileInputStream(commandLineParser.input))
    val reportConfig = ReportConfig(
        listOf(
            HtmlConfig(
                true,
                outputLocation,
                HtmlConfig.Script(
                    commandLineParser.mermaidScriptSrc,
                    commandLineParser.embed,
                    HtmlConfig.Script.Options(commandLineParser.maxTextSize)
                )
            ),
        ),
        ReportConfig.Marks(),
        false,
    )
    ReportCreator(logger).createReports(report, reportConfig, File(commandLineParser.outputDir), commandLineParser.taskName)
}
