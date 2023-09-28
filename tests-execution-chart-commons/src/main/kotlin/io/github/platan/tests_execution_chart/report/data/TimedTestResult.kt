package io.github.platan.tests_execution_chart.report.data

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class TimedTestResult(
    // TODO change to val
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var resultType: String,
    var type: Type,
    var parentName: String?
) {

    fun shiftTimestamps(timeShift: Duration): TimedTestResult {
        return this.copy(
            startTime = this.startTime + timeShift.toMillis(),
            endTime = this.endTime + timeShift.toMillis()
        )
    }

    val durationMs: Long
        get() = this.endTime - this.startTime

    enum class Type {
        SUITE, TEST
    }
}
