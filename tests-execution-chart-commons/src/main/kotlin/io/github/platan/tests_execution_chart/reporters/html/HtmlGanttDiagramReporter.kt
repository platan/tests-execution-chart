package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.config.HtmlConfig
import io.github.platan.tests_execution_chart.reporters.mermaid.TestExecutionMermaidDiagramFormatter
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

private const val TEMPLATE_HTML_FILE = "template.html"
private const val GRAPH_PLACEHOLDER = "@GRAPH_PLACEHOLDER@"
private const val MAX_TEXT_SIZE_PLACEHOLDER = "@MAX_TEXT_SIZE@"
private const val MERMAID_JS_FILE_NAME = "mermaid.min.js"
private const val MERMAID_SRC_PLACEHOLDER = "@MERMAID_SRC@"
private const val TABLE_SRC_PLACEHOLDER = "@TABLE@"

class HtmlGanttDiagramReporter(private val logger: Logger) :
    GanttDiagramReporter<HtmlConfig>() {


    override fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String) {
        val resource: URL? = this::class.java.classLoader.getResource(TEMPLATE_HTML_FILE)
        val template: String
        if (resource == null) {
            throw RuntimeException("$TEMPLATE_HTML_FILE not found")
        } else {
            template = resource.readText(Charsets.UTF_8)
        }
        var scriptSrc = config.script.src
        if (config.script.embed) {
            val reportsDir = prepareReportsDir(baseDir, config.outputLocation)
            val scriptFileName = MERMAID_JS_FILE_NAME
            downloadFile(URL(scriptSrc), "${reportsDir.absolutePath}/$scriptFileName")
            scriptSrc = scriptFileName
        }
        val maxTextSize = config.script.options.maxTextSize
        val htmlReport = prepareHtmlReport(report, template, scriptSrc, maxTextSize)
        val reportFile = save(htmlReport, taskName, baseDir, config.outputLocation, "html")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    override fun getConfigType(): Class<out ReportConfig.Format> = HtmlConfig::class.java

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
