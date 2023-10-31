package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import java.io.File
import java.util.*


class ReportCreator(private val logger: Logger, private val availableReporters: Iterable<GanttDiagramReporter<*>>) {

    constructor(logger: Logger) : this(logger, ServiceLoader.load(GanttDiagramReporter::class.java))

    fun createReports(
        results: TestExecutionScheduleReport,
        reportConfig: ReportConfig,
        buildDir: File,
        taskName: String
    ) {
        val adjustedResults = ReportConfigurator().configure(results, reportConfig)
        logger.lifecycle("Tests execution schedule report for task '$taskName'")
        val configsByType = reportConfig.formatsList.associateBy { it.javaClass }
        val enabledReporters = availableReporters.filter { reporter ->
            val config = configsByType[reporter.getConfigType()]
            if (config == null) {
                false
            } else {
                reporter.setConfiguration(config)
                reporter.logger = logger
                // TODO ask reporter if it's enabled
                config.enabled
            }
        }
        enabledReporters.forEach { reporter ->
            reporter.report(adjustedResults, buildDir, taskName)
        }
    }

}