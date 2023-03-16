package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.config.HtmlConfig
import io.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import java.io.File

fun main() {
    println("test")
    val config = HtmlConfig(
        HtmlConfig.Format(true, "html-report"),
        HtmlConfig.Script(
            "https://cdn.jsdelivr.net/npm/mermaid@9.4.3/dist/mermaid.min.js",
            false,
            HtmlConfig.Script.Options(50000)
        )
    )
    val logger = object : Logger {
        override fun lifecycle(message: String) {
            println(message)
        }
    }
    val report = TestExecutionScheduleReport()
    report.add("Tests", "test 1", 2, 4, "SUCCESS")
    report.add("Tests", "test 2", 2, 6, "SUCCESS")
    report.add("Tests", "test 3", 2, 3, "FAILUER")
    HtmlGanttDiagramReporter(config, logger).report(report, File("."), "task1")
}
