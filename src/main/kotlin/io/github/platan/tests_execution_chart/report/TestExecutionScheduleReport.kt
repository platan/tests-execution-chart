package io.github.platan.tests_execution_chart.report

import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Serializable
data class TestExecutionScheduleReport(val results: List<TimedTestResult>, val marks: List<Mark>) {

    constructor(results: List<TimedTestResult>) : this(results, emptyList())

    fun timestampsShiftedToStartOfDay(zoneId: ZoneId): TestExecutionScheduleReport {
        return when {
            results.isEmpty() -> this
            else -> {
                val minStartTime = Instant.ofEpochMilli(results.minBy { it.startTime }.startTime)
                val targetMinStartTime = minStartTime.atZone(zoneId).truncatedTo(ChronoUnit.DAYS).toInstant()
                val timeShift = Duration.between(minStartTime, targetMinStartTime)
                return TestExecutionScheduleReport(
                    results.map { it.shiftTimestamps(timeShift) },
                    marks.map { it.shiftTimestamps(timeShift) }
                )
            }
        }
    }

    fun addTotalTimeOfAllTestsMark(markName: String): TestExecutionScheduleReport {
        val minStartTime = results.minBy { it.startTime }.startTime
        val totalDurationOfAllTestsMs = results.sumOf { it.durationMs }
        val totalTimeOfAllTestsMark = minStartTime + totalDurationOfAllTestsMs
        return this.copy(marks = listOf(Mark(markName, totalTimeOfAllTestsMark)))
    }
}
