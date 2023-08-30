package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.gradle.config.Mermaid
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.Logger
import java.io.File

internal class MermaidTestsReporter(private val config: Mermaid, private val logger: Logger) : GanttDiagramReporter() {

    override fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String) {
        val mermaidReport = TestExecutionMermaidDiagramFormatter().format(report)
        val reportFile = save(mermaidReport, taskName, baseDir, config.outputLocation.get(), "txt")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }
}
