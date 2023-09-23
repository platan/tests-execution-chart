package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.ReportConfig

data class HtmlConfig(override val enabled: Boolean, override val outputLocation: String, val script: Script) :
    ReportConfig.Format {
    data class Script(val src: String, val embed: Boolean, val options: Options) {
        data class Options(val maxTextSize: Int)
    }
}
