package io.github.platan.tests_execution_chart.report

import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    var name: String,
    var timestamp: Long
)
