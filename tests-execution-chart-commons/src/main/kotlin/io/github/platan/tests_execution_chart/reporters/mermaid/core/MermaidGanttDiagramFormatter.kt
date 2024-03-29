package io.github.platan.tests_execution_chart.reporters.mermaid.core

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
        diagram.entries.forEach { entry ->
            when (entry) {
                is MermaidGanttDiagram.Section -> {
                    ganttDiagram.append("section ${escape(entry.name)}\n")
                }

                is MermaidGanttDiagram.Task -> {
                    var status = ""
                    if (entry.type != null) {
                        status = "${entry.type}, "
                    }
                    val end = format.format(Instant.ofEpochMilli(entry.end))
                    val start = format.format(Instant.ofEpochMilli(entry.start))
                    ganttDiagram.append("${escape(entry.name)} :${status}$start, $end\n")
                }

                is MermaidGanttDiagram.Milestone -> {
                    val timestamp = format.format(Instant.ofEpochMilli(entry.timestamp))
                    ganttDiagram.append("${escape(entry.name)} : milestone, $timestamp, 0\n")
                }
            }
        }
        return ganttDiagram.toString()
    }

    private fun escape(str: String): String {
        return str
            // warning: order of replacements below matters
            // replace ; before replacing other characters with entity containing ;
            .replace(";", "#semi;")
            // replace # but not in #semi;
            .replace(Regex("#(?!semi;)"), "#35;")
            .replace(":", "#colon;") // https://github.com/mermaid-js/mermaid/issues/742
    }
}
