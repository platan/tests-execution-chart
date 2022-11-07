package io.github.platan.tests_execution_chart.reporters.mermaid

import java.text.SimpleDateFormat
import java.util.Date

internal class MermaidGanttDiagramFormatter {

    fun format(diagram: MermaidGanttDiagram, dateFormat: String): String {
        val ganttDiagram = StringBuilder()
        ganttDiagram.append("gantt\n")
        ganttDiagram.append("dateFormat ${diagram.dateFormat}\n")
        ganttDiagram.append("axisFormat ${diagram.axisFormat}\n")
        val format = SimpleDateFormat(dateFormat)
        diagram.sections.forEach { section ->
            ganttDiagram.append("section ${escape(section.name)}\n")
            section.rows.forEach { row ->
                var status = ""
                if (row.type != null) {
                    status = "${row.type}, "
                }
                val end = format.format(Date(row.end))
                val start = format.format(Date(row.start))
                ganttDiagram.append("${escape(row.name)} :${status}$start, $end\n")
            }
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
