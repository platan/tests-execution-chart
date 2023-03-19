package io.github.platan.tests_execution_chart

import kotlinx.serialization.Serializable

@Serializable
class TestExecutionScheduleReport {
    var results = mutableListOf<TimedTestResult>()
    fun add(className: String?, testName: String, startTime: Long, endTime: Long, resultType: String) {
        results.add(TimedTestResult(className, testName, startTime, endTime, resultType))
    }
}
