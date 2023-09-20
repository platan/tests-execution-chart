package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.reporters.config.HtmlConfig
import io.github.platan.tests_execution_chart.reporters.config.JsonConfig
import io.github.platan.tests_execution_chart.reporters.config.MermaidConfig

data class ReportConfig(val formats: Formats, val marks: Marks, val shiftTimestampsToStartOfDay: Boolean = false) {
    data class Marks(val totalTimeOfAllTests: Mark) {
        data class Mark(val enabled: Boolean, val name: String)
    }

    data class Formats(val mermaid: MermaidConfig, val html: HtmlConfig, val json: JsonConfig)
}