package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.json.JsonReporter
import io.github.platan.tests_execution_chart.reporters.mermaid.MermaidTestsReporter
import java.io.File

class ReportCreator(private val logger: Logger) {

    fun createReports(
        results: TestExecutionScheduleReport,
        reportConfig: ReportConfig,
        buildDir: File,
        taskName: String
    ) {
        val adjustedResults = ReportConfigurator().configure(results, reportConfig)
        logger.lifecycle("Tests execution schedule report for task '$taskName'")
        val reporters: MutableList<GanttDiagramReporter> = mutableListOf()
        val mermaidConfig = reportConfig.formats.mermaid
        if (mermaidConfig.format.enabled) {
            reporters.add(MermaidTestsReporter(mermaidConfig, logger))
        }
        val jsonConfig = reportConfig.formats.json
        if (jsonConfig.format.enabled) {
            reporters.add(JsonReporter(jsonConfig, logger))
        }
        val htmlConfig = reportConfig.formats.html
        if (htmlConfig.format.enabled) {
            reporters.add(HtmlGanttDiagramReporter(htmlConfig, logger))
        }
        reporters.forEach {
            it.report(adjustedResults, buildDir, taskName)
        }
    }

}