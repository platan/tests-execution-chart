package io.github.platan.tests_execution_chart.reporters.json

import groovy.json.JsonOutput
import io.github.platan.tests_execution_chart.config.Json
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.Test

class JsonReporter(private val config: Json, private val logger: Logger) : GanttDiagramReporter() {

    override fun report(
        report: TestExecutionScheduleReport,
        task: Test
    ) {
        val jsonReport = JsonOutput.prettyPrint(JsonOutput.toJson(report))
        val reportFile = save(task, jsonReport, config.outputLocation.get(), "json")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }
}
