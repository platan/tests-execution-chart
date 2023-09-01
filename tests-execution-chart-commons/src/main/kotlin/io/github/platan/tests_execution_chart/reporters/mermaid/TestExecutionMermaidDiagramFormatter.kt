package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.mermaid.core.MermaidGanttDiagram
import io.github.platan.tests_execution_chart.reporters.mermaid.core.MermaidGanttDiagramFormatter

class TestExecutionMermaidDiagramFormatter {

    companion object {
        val types = mapOf("FAILURE" to "crit", "SUCCESS" to "active")
    }

    fun format(report: TestExecutionScheduleReport): String {
        val diagramBuilder = MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        report.results.groupBy { result -> result.className }.forEach { (className, results) ->
            diagramBuilder.addSection(className.orEmpty())
            results.forEach {
                val testNameWithDuration = "${it.testName} - ${it.endTime.minus(it.startTime)} ms"
                diagramBuilder.addTask(
                    testNameWithDuration,
                    types[it.resultType],
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
