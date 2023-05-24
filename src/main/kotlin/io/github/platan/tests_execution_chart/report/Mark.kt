package io.github.platan.tests_execution_chart.report

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class Mark(
    var name: String,
    var timestamp: Long
) {
    fun shiftTimestamps(timeShift: Duration): Mark {
        return this.copy(timestamp = this.timestamp + timeShift.toMillis())
    }
}
