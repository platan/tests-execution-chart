package com.github.platan.testsganttchart.reporters.html

import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.Test
import com.github.platan.testsganttchart.TestExecutionScheduleReport
import com.github.platan.testsganttchart.config.Html
import com.github.platan.testsganttchart.reporters.GanttDiagramReporter
import com.github.platan.testsganttchart.reporters.mermaid.TestExecutionMermaidDiagramFormatter
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

private const val TEMPLATE_HTML_FILE = "template.html"
private const val GRAPH_PLACEHOLDER = "@GRAPH_PLACEHOLDER@"
private const val MERMAID_JS_FILE_NAME = "mermaid.min.js"
private const val MERMAID_SRC_PLACEHOLDER = "@MERMAID_SRC@"

internal class HtmlGanttDiagramReporter(private val config: Html, private val logger: Logger) : GanttDiagramReporter() {
    override fun report(report: TestExecutionScheduleReport, task: Test) {
        val mermaid = TestExecutionMermaidDiagramFormatter().format(report)
        val resource: URL? = this::class.java.classLoader.getResource(TEMPLATE_HTML_FILE)
        val template: String
        if (resource == null) {
            throw RuntimeException("$TEMPLATE_HTML_FILE not found")
        } else {
            template = resource.readText(Charsets.UTF_8)
        }
        var src = config.getScript().src.get()
        if (config.getScript().embed.get()) {
            val reportsDir = prepareReportsDir(task, config.outputLocation.get())
            val scriptFileName = MERMAID_JS_FILE_NAME
            downloadFile(URL(src), "${reportsDir.absolutePath}/$scriptFileName")
            src = scriptFileName
        }
        val htmlReport = template.replace(GRAPH_PLACEHOLDER, mermaid)
            .replace(MERMAID_SRC_PLACEHOLDER, src)

        val reportFile = save(task, htmlReport, config.outputLocation.get(), "html")
        logger.lifecycle("Gantt chart for test execution schedule saved to ${reportFile.absolutePath} file.")
    }

    private fun downloadFile(url: URL, path: String) {
        url.openStream().use { Files.copy(it, Paths.get(path)) }
    }
}