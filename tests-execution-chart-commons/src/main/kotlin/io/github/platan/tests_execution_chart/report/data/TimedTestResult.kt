package io.github.platan.tests_execution_chart.report.data

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class TimedTestResult(
    val className: String?,
    val testName: String,
    val startTime: Long,
    val endTime: Long,
    val resultType: String,
    val type: Type,
    val parentName: String?
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
