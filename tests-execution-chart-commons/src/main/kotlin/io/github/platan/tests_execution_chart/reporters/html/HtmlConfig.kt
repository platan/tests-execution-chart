package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.ReportConfig

data class HtmlConfig(override val enabled: Boolean, override val outputLocation: String, val script: Script) :
    ReportConfig.Format {
    data class Script(val src: String, val embed: Boolean, val options: Options) {

        companion object {
            private const val DEFAULT_MERMAID_SCRIPT_SRC =
                "https://cdn.jsdelivr.net/npm/mermaid@10.5.0/dist/mermaid.min.js"

            fun getSrcDefault(): String = DEFAULT_MERMAID_SCRIPT_SRC
        }

        data class Options(val maxTextSize: Int)
    }
}
