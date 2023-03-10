package io.github.platan.tests_execution_chart.report

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

data class TestExecutionScheduleReport(val results: List<TimedTestResult>) {
    fun timestampsShiftedToStartOfDay(): TestExecutionScheduleReport {
        return when {
            results.isEmpty() -> this
            else -> {
                val minStartTime = Instant.ofEpochMilli(results.minBy { it.startTime }.startTime)
                val targetMinStartTime = minStartTime.truncatedTo(ChronoUnit.DAYS)
                val timeShift = Duration.between(targetMinStartTime, minStartTime)
                return TestExecutionScheduleReport(results.map { it.shiftTimestamps(timeShift) })
            }
        }
    }
}
