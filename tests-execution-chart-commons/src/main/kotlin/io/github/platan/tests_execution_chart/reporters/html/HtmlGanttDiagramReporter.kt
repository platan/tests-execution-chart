package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.SingleFileReportWriter
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

const val MERMAID_JS_FILE_NAME = "mermaid.min.js"


class HtmlGanttDiagramReporter :
    GanttDiagramReporter<HtmlConfig>() {


    override fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String) {
        var scriptSrc = config.script.src
        if (config.script.embed) {
            val reportsDir = prepareReportsDir(baseDir, config.outputLocation)
            val scriptFileName = MERMAID_JS_FILE_NAME
            downloadFile(URL(scriptSrc), "${reportsDir.absolutePath}/$scriptFileName")
            scriptSrc = scriptFileName
        }
        val maxTextSize = config.script.options.maxTextSize

        val htmlReport = HtmlGanttDiagramFormatter().formatHtml(report, scriptSrc, maxTextSize)
        val reportFile = SingleFileReportWriter().save(htmlReport, taskName, baseDir, config.outputLocation, "html")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    override fun getConfigType(): Class<out ReportConfig.Format> = HtmlConfig::class.java

    private fun prepareReportsDir(baseDir: File, location: String): File {
        val reportsDir = File(baseDir, location)
        reportsDir.mkdirs()
        return reportsDir
    }


    private fun downloadFile(url: URL, path: String) {
        url.openStream().use { Files.copy(it, Paths.get(path)) }
    }
}
