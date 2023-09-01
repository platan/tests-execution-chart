package io.github.platan.tests_execution_chart.report

data class ReportConfig(val shiftTimestampsToStartOfDay: Boolean, val marks: Marks) {
    data class Marks(val totalTimeOfAllTests: Mark) {
        data class Mark(val enabled: Boolean, val name: String)
    }
}