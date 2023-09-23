package io.github.platan.tests_execution_chart.reporters.json

import groovy.json.JsonOutput
import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import java.io.File

class JsonReporter : GanttDiagramReporter<JsonConfig>() {


    override fun report(
        report: TestExecutionScheduleReport,
        baseDir: File,
        taskName: String
    ) {
        val jsonReport = JsonOutput.prettyPrint(JsonOutput.toJson(report))
        val reportFile = save(jsonReport, taskName, baseDir, config.outputLocation, "json")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    override fun getConfigType(): Class<out ReportConfig.Format> = JsonConfig::class.java
}
