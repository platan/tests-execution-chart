package com.github.platan.testsganttchart

data class MyTestResult(
    var className: String?,
    var testName: String,
    var startTime: Long,
    var endTime: Long,
    var resultType: String
)