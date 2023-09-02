package io.github.platan.tests_execution_chart.report

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
        val mermaidConfig = reportConfig.formats.mermaid
        if (mermaidConfig.format.enabled) {
            MermaidTestsReporter(mermaidConfig, logger).report(
                adjustedResults,
                buildDir,
                taskName
            )
        }
        val jsonConfig = reportConfig.formats.json
        if (jsonConfig.format.enabled) {
            JsonReporter(jsonConfig, logger).report(
                adjustedResults,
                buildDir,
                taskName
            )
        }
        val htmlConfig = reportConfig.formats.html
        if (htmlConfig.format.enabled) {
            HtmlGanttDiagramReporter(htmlConfig, logger).report(
                adjustedResults,
                buildDir,
                taskName
            )
        }
    }

}