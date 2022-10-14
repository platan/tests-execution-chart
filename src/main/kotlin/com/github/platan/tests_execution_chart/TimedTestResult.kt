package com.github.platan.tests_execution_chart

data class TimedTestResult(
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var resultType: String,
) {
    val duration: Long
        get() = this.endTime - this.startTime
}
