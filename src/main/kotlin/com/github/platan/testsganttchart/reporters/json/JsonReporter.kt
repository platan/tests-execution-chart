package com.github.platan.testsganttchart.reporters.json

import groovy.json.JsonOutput
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.Test
import com.github.platan.testsganttchart.TestExecutionScheduleReport
import com.github.platan.testsganttchart.config.Json
import com.github.platan.testsganttchart.reporters.GanttDiagramReporter
import java.io.File

class JsonReporter(private val config: Json, private val logger: Logger) : GanttDiagramReporter() {

    override fun report(
        report: TestExecutionScheduleReport,
        task: Test,
    ) {
        val jsonReport = JsonOutput.prettyPrint(JsonOutput.toJson(report))
        val reportFile = save(task, jsonReport, config.outputLocation.get(), "json")
        logger.lifecycle("Gantt chart for test execution schedule saved to ${reportFile.absolutePath} file.")
    }
}
