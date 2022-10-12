package com.github.platan.tests_execution_chart.reporters.mermaid

import com.github.platan.tests_execution_chart.TestExecutionScheduleReport

class TestExecutionMermaidDiagramFormatter {

    companion object {
        val types = mapOf("FAILURE" to "crit", "SUCCESS" to "active")
    }

    fun format(report: TestExecutionScheduleReport): String {
        val diagramBuilder = MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        report.results.forEach {
            diagramBuilder.add(it.className!!, it.testName, types[it.resultType], it.startTime, it.endTime)
        }
        val diagram = diagramBuilder.build("YYYY-MM-DD\\THH\\:mm\\:ss\\.SSSZ", "%H:%M:%S.%L")
        return MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }
}