package io.github.platan.tests_execution_chart.report.data

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class TimedTestResult(
    // TODO deprecate, rename to groupName
    val className: String?,
    val testName: String,
    val startTime: Long,
    val endTime: Long,
    // TODO deprecate, rename to result
    val resultType: String,
    val type: Type
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
