package io.github.platan.tests_execution_chart.report

data class ReportConfig(
    val formatsList: List<Format>,
    val marks: Marks,
    val shiftTimestampsToStartOfDay: Boolean = false,
) {
    data class Marks(val totalTimeOfAllTests: Mark = DISABLED) {
        data class Mark(val enabled: Boolean, val name: String)
        companion object {
            val DISABLED: Mark = Mark(false, "")
        }
    }

    interface Format {
        val enabled: Boolean
        val outputLocation: String
    }
}