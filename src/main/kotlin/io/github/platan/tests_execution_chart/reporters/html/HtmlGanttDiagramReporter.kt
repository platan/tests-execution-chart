package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.config.Html
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.mermaid.TestExecutionMermaidDiagramFormatter
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.Test
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

private const val TEMPLATE_HTML_FILE = "template.html"
private const val GRAPH_PLACEHOLDER = "@GRAPH_PLACEHOLDER@"
private const val MAX_TEXT_SIZE_PLACEHOLDER = "@MAX_TEXT_SIZE@"
private const val MERMAID_JS_FILE_NAME = "mermaid.min.js"
private const val MERMAID_SRC_PLACEHOLDER = "@MERMAID_SRC@"
private const val TABLE_SRC_PLACEHOLDER = "@TABLE@"

internal class HtmlGanttDiagramReporter(private val config: Html, private val logger: Logger) : GanttDiagramReporter() {
    override fun report(report: TestExecutionScheduleReport, task: Test) {
        val resource: URL? = this::class.java.classLoader.getResource(TEMPLATE_HTML_FILE)
        val template: String
        if (resource == null) {
            throw RuntimeException("$TEMPLATE_HTML_FILE not found")
        } else {
            template = resource.readText(Charsets.UTF_8)
        }
        var scriptSrc = config.getScript().src.get()
        if (config.getScript().embed.get()) {
            val reportsDir = prepareReportsDir(task, config.outputLocation.get())
            val scriptFileName = MERMAID_JS_FILE_NAME
            downloadFile(URL(scriptSrc), "${reportsDir.absolutePath}/$scriptFileName")
            scriptSrc = scriptFileName
        }
        val maxTextSize = config.getScript().getConfig().maxTextSize.get()
        val htmlReport = prepareHtmlReport(report, template, scriptSrc, maxTextSize)
        val reportFile = save(task, htmlReport, config.outputLocation.get(), "html")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    private fun prepareHtmlReport(
        report: TestExecutionScheduleReport,
        template: String,
        src: String,
        maxTextSize: Int
    ): String {
        val mermaid = TestExecutionMermaidDiagramFormatter().format(report)
        val escapedMermaid = mermaid
            .replace("\\", "\\\\")
            // Mermaid data is put in javascript code and specified using backtick character (`).
            // We have to escape ` character.
            .replace("`", "\\`")
        val table = HtmlTableFormatter().format(report)
        return template
            .replace(GRAPH_PLACEHOLDER, escapedMermaid)
            .replace(MERMAID_SRC_PLACEHOLDER, src)
            .replace(MAX_TEXT_SIZE_PLACEHOLDER, maxTextSize.toString())
            .replace(TABLE_SRC_PLACEHOLDER, table)
    }

    private fun downloadFile(url: URL, path: String) {
        url.openStream().use { Files.copy(it, Paths.get(path)) }
    }
}
