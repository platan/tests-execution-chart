package com.github.platan.testsganttchart

class TestExecutionScheduleReport {
    var results = mutableListOf<MyTestResult>()
    fun add(className: String?, testName: String, startTime: Long, endTime: Long, resultType: String) {
        results.add(MyTestResult(className, testName, startTime, endTime, resultType))
    }
}