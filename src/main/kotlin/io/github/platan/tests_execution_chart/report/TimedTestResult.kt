package io.github.platan.tests_execution_chart.report

import java.time.Duration

data class TimedTestResult(
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var resultType: String
) {

    fun shiftTimestamps(timeShift: Duration): TimedTestResult {
        return this.copy(
            startTime = this.startTime - timeShift.toMillis(),
            endTime = this.endTime - timeShift.toMillis()
        )
    }

    val durationMs: Long
        get() = this.endTime - this.startTime
}
