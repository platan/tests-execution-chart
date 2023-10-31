package io.github.platan.tests_execution_chart.reporters

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport

interface TestExecutionReportFormatter {
    fun format(report: TestExecutionScheduleReport): String
}
