package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
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
        // TODO discover reporter
        val availableReporters =
            listOf(
                MermaidTestsReporter(),
                JsonReporter(),
                HtmlGanttDiagramReporter()
            )
        val configsByType = reportConfig.formatsList.groupBy { it.javaClass }
        val enabledReporters = availableReporters.filter { reporter ->
            // TODO ask reporter if it's enabled
            configsByType.containsKey(reporter.getConfigType()) && configsByType[reporter.getConfigType()]!!.first().enabled
        }
        enabledReporters.forEach { reporter ->
            val configuration = configsByType[reporter.getConfigType()]!!.first()
            reporter.setConfiguration(configuration)
            reporter.logger = logger
        }
        enabledReporters.forEach { reporter ->
            reporter.report(adjustedResults, buildDir, taskName)
        }
    }

}