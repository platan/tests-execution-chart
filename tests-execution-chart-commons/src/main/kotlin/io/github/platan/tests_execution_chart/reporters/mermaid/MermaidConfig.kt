package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.ReportConfig.Format

data class MermaidConfig(override val enabled: Boolean, override val outputLocation: String) : Format
