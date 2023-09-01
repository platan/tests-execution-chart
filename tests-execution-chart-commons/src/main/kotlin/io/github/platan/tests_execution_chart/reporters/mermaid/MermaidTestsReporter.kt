package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.config.MermaidConfig
import java.io.File

class MermaidTestsReporter(private val config: MermaidConfig, private val logger: Logger) :
    GanttDiagramReporter() {

    override fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String) {
        val mermaidReport = TestExecutionMermaidDiagramFormatter().format(report)
        val reportFile = save(mermaidReport, taskName, baseDir, config.format.outputLocation, "txt")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }
}
