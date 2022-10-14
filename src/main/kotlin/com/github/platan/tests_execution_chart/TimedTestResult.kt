package com.github.platan.tests_execution_chart

data class TimedTestResult(
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var duration: Long,
    var resultType: String
)
