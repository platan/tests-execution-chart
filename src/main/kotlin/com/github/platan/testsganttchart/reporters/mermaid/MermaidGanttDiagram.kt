package com.github.platan.testsganttchart.reporters.mermaid

data class MermaidGanttDiagram(val dateFormat: String, val axisFormat: String, val sections: List<Section>) {
    data class Section(val name: String, val rows: List<Row>) {
        data class Row(val name: String, val type: String?, val start: Long, val end: Long)
    }

    class MermaidGanttDiagramBuilder {
        private val sectionsMap: MutableMap<String, MutableList<Section.Row>> = mutableMapOf()
        fun add(sectionName: String, rowName: String, type: String?, start: Long, end: Long) {
            val section: MutableList<Section.Row> =
                sectionsMap.getOrPut(sectionName) { mutableListOf() }
            section.add(Section.Row(rowName, type, start, end))
        }

        fun build(dateFormat: String, axisFormat: String): MermaidGanttDiagram {
            return MermaidGanttDiagram(dateFormat, axisFormat, sectionsMap.entries.map { sectionEntry ->
                Section(
                    sectionEntry.key,
                    sectionEntry.value.map { row ->
                        Section.Row(
                            row.name,
                            row.type,
                            row.start,
                            row.end
                        )
                    })
            }
            )
        }
    }
}