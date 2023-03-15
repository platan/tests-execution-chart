package io.github.platan.tests_execution_chart.reporters.config

data class HtmlConfig(val format: Format, val script: Script) {
    data class Format(val enabled: Boolean, val outputLocation: String)
    data class Script(val src: String, val embed: Boolean, val options: Options) {
        data class Options(val maxTextSize: Int)
    }
}
