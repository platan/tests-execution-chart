package com.github.platan.testsganttchart.reporters.mermaid

import java.text.SimpleDateFormat
import java.util.*

internal class MermaidGanttDiagramFormatter {

    fun format(diagram: MermaidGanttDiagram, dateFormat: String): String {
        val ganttDiagram = StringBuilder()
        ganttDiagram.append("gantt\n")
        ganttDiagram.append("dateFormat ${diagram.dateFormat}\n")
        ganttDiagram.append("axisFormat ${diagram.axisFormat}\n")
        val format = SimpleDateFormat(dateFormat)
        diagram.sections.forEach { section ->
            ganttDiagram.append("section ${section.name}\n")
            section.rows.forEach { row ->
                var status = ""
                if (row.type != null) {
                    status = "${row.type}, "
                }
                val end = format.format(Date(row.end))
                val start = format.format(Date(row.start))
                ganttDiagram.append("${row.name} :${status}$start, $end\n")
            }
        }
        return ganttDiagram.toString()
    }
}