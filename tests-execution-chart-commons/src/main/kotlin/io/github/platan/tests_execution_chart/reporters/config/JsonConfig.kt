package io.github.platan.tests_execution_chart.reporters.config

import io.github.platan.tests_execution_chart.report.ReportConfig

data class JsonConfig(override val enabled: Boolean, override val outputLocation: String) : ReportConfig.Format {

    constructor(enabled: Boolean) : this(enabled, "")
}
