package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.mermaid.TestExecutionMermaidDiagramFormatter
import java.net.URL

private const val TEMPLATE_HTML_FILE = "template.html"
private const val GRAPH_PLACEHOLDER = "@GRAPH_PLACEHOLDER@"
private const val MAX_TEXT_SIZE_PLACEHOLDER = "@MAX_TEXT_SIZE@"
private const val MERMAID_SRC_PLACEHOLDER = "@MERMAID_SRC@"
private const val TABLE_SRC_PLACEHOLDER = "@TABLE@"

class HtmlGanttDiagramFormatter {

    fun formatHtml(
        report: TestExecutionScheduleReport,
        scriptSrc: String,
        maxTextSize: Int
    ): String {
        val template: String = loadTemplate(TEMPLATE_HTML_FILE)
        return prepareHtmlReport(report, template, scriptSrc, maxTextSize)
    }

    private fun loadTemplate(templatePath: String): String {
        val resource: URL? = this::class.java.classLoader.getResource(templatePath)
        val template: String
        if (resource == null) {
            throw RuntimeException("$templatePath not found")
        } else {
            template = resource.readText(Charsets.UTF_8)
        }
        return template
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

}