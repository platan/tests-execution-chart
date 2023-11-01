package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.report.data.Mark
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.report.data.TimedTestResult

class TestExecutionScheduleReportBuilder {
    private var results = mutableListOf<TimedTestResult>()
    private var marks = mutableListOf<Mark>()
    fun addResult(
        className: String?,
        testName: String,
        startTime: Long,
        endTime: Long,
        resultType: String,
        type: TimedTestResult.Type
    ): TestExecutionScheduleReportBuilder {
        results.add(TimedTestResult(className, testName, startTime, endTime, resultType, type))
        return this
    }

    fun addMark(name: String, timestamp: Long): TestExecutionScheduleReportBuilder {
        marks.add(Mark(name, timestamp))
        return this
    }

    fun build(): TestExecutionScheduleReport {
        return TestExecutionScheduleReport(results, marks)
    }
}
