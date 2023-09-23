package io.github.platan.tests_execution_chart.report

data class ReportConfig(
    val marks: Marks,
    val shiftTimestampsToStartOfDay: Boolean = false,
    val formatsList: List<Format> = emptyList()
) {
    data class Marks(val totalTimeOfAllTests: Mark) {
        data class Mark(val enabled: Boolean, val name: String)
    }

    interface Format {
        val enabled: Boolean
        val outputLocation: String
    }
}