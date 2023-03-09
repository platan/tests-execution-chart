package io.github.platan.tests_execution_chart.report

import java.time.Duration

data class TimedTestResult(
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var resultType: String
) {

    fun shiftTimestamps(duration: Duration): TimedTestResult {
        return this.copy(
            startTime = this.startTime - duration.toMillis(),
            endTime = this.endTime - duration.toMillis()
        )
    }

    val durationMs: Long
        get() = this.endTime - this.startTime
}
