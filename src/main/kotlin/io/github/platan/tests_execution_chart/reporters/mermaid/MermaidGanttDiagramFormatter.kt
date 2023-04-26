package io.github.platan.tests_execution_chart.reporters.mermaid

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal class MermaidGanttDiagramFormatter {

    fun format(diagram: MermaidGanttDiagram, dateFormat: String): String {
        val ganttDiagram = StringBuilder()
        ganttDiagram.append("gantt\n")
        ganttDiagram.append("dateFormat ${diagram.dateFormat}\n")
        ganttDiagram.append("axisFormat ${diagram.axisFormat}\n")
        val format = DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneId.systemDefault())
        diagram.sections.forEach { section ->
            ganttDiagram.append("section ${escape(section.name)}\n")
            section.rows.forEach { row ->
                var status = ""
                if (row.type != null) {
                    status = "${row.type}, "
                }
                val end = format.format(Instant.ofEpochMilli(row.end))
                val start = format.format(Instant.ofEpochMilli(row.start))
                ganttDiagram.append("${escape(row.name)} :${status}$start, $end\n")
            }
        }
        diagram.milestones.forEach { milestone ->
            val timestamp = format.format(Instant.ofEpochMilli(milestone.timestamp))
            ganttDiagram.append("${milestone.name} : milestone, $timestamp, 0\n")
        }
        return ganttDiagram.toString()
    }

    private fun escape(str: String): String {
        return str
            // remove # before replacing other characters with entity containing #
            .replace("#", "")
            // replace ; before replacing other characters with entity containing ;
            .replace(";", "#semi;")
            .replace(":", "#colon;") // https://github.com/mermaid-js/mermaid/issues/742
    }
}
