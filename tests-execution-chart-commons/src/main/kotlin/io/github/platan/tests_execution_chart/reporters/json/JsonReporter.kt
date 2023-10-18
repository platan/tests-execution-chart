package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonReporter : GanttDiagramReporter<JsonConfig>() {
    private val json = Json {
        prettyPrint = true
    }

    override fun report(
        report: TestExecutionScheduleReport,
        baseDir: File,
        taskName: String
    ) {
        val jsonReport = json.encodeToString(report)
        val reportFile = save(jsonReport, taskName, baseDir, config.outputLocation, "json")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    override fun getConfigType(): Class<out ReportConfig.Format> = JsonConfig::class.java
}
