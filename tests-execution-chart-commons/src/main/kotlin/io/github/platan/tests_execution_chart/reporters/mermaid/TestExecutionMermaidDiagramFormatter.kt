package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.report.data.TimedTestResult
import io.github.platan.tests_execution_chart.reporters.mermaid.core.MermaidGanttDiagram
import io.github.platan.tests_execution_chart.reporters.mermaid.core.MermaidGanttDiagramFormatter

class TestExecutionMermaidDiagramFormatter {

    companion object {
        val taskFormat: Map<TimedTestResult.Type, Map<String, String>> = mapOf(
            TimedTestResult.Type.TEST to mapOf("FAILURE" to "crit", "SUCCESS" to "active"),
            TimedTestResult.Type.SUITE to mapOf("FAILURE" to "crit,done", "SUCCESS" to "done")
        )
    }

    fun format(report: TestExecutionScheduleReport): String {
        val diagramBuilder = MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        report.results.groupBy { result -> result.className }.forEach { (className, results) ->
            diagramBuilder.addSection(className.orEmpty())
            results.forEach {
                val testNameWithDuration = "${it.testName} - ${it.endTime.minus(it.startTime)} ms"
                diagramBuilder.addTask(
                    testNameWithDuration,
                    requireNotNull(taskFormat[it.type]) { "No mapping for `${it.type}`." }[it.resultType],
                    it.startTime,
                    it.endTime
                )
            }
        }
        report.marks.forEach { mark ->
            diagramBuilder.addMilestone(mark.name, mark.timestamp)
        }
        val diagram = diagramBuilder.build("YYYY-MM-DDTHH:mm:ss.SSSZZ", "%H:%M:%S.%L")
        return MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }
}
