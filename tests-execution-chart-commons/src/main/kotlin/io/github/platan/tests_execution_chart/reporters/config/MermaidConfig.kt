package io.github.platan.tests_execution_chart.reporters.config

data class MermaidConfig(val format: Format) {
    data class Format(val enabled: Boolean, val outputLocation: String)
}
