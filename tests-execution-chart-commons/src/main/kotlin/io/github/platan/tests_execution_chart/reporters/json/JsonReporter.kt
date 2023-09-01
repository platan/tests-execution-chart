package io.github.platan.tests_execution_chart.reporters.json

import groovy.json.JsonOutput
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.config.JsonConfig
import java.io.File

class JsonReporter(private val config: JsonConfig, private val logger: Logger) : GanttDiagramReporter() {

    override fun report(
        report: TestExecutionScheduleReport,
        baseDir: File,
        taskName: String
    ) {
        val jsonReport = JsonOutput.prettyPrint(JsonOutput.toJson(report))
        val reportFile = save(jsonReport, taskName, baseDir, config.format.outputLocation, "json")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }
}
