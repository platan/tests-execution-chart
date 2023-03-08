package io.github.platan.tests_execution_chart.report

class TestExecutionScheduleReportBuilder {
    private var results = mutableListOf<TimedTestResult>()
    fun add(className: String?, testName: String, startTime: Long, endTime: Long, resultType: String) {
        results.add(TimedTestResult(className, testName, startTime, endTime, resultType))
    }

    fun getResults(): TestExecutionScheduleReport {
        return TestExecutionScheduleReport(results)
    }
}
