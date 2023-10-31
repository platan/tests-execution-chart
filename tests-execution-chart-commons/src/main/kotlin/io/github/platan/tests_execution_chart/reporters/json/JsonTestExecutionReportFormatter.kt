package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.TestExecutionReportFormatter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonTestExecutionReportFormatter : TestExecutionReportFormatter {
    private val json = Json {
        prettyPrint = true
    }

    override fun format(report: TestExecutionScheduleReport): String {
        return json.encodeToString(report)
    }
}