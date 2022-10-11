package com.github.platan.testsganttchart.reporters.mermaid

import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.Test
import com.github.platan.testsganttchart.TestExecutionScheduleReport
import com.github.platan.testsganttchart.config.Mermaid
import com.github.platan.testsganttchart.reporters.GanttDiagramReporter

internal class MermaidTestsReporter(private val config: Mermaid, private val logger: Logger) : GanttDiagramReporter() {

    override fun report(report: TestExecutionScheduleReport, task: Test) {
        val mermaidReport = TestExecutionMermaidDiagramFormatter().format(report)
        val reportFile = save(task, mermaidReport, config.outputLocation.get(), "txt")
        logger.lifecycle("Gantt chart for test execution schedule saved to ${reportFile.absolutePath} file.")
    }

}