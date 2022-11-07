package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.TestExecutionScheduleReport
import org.apache.commons.text.StringEscapeUtils.escapeHtml4

class HtmlTableFormatter {

    fun format(report: TestExecutionScheduleReport): String {
        val tbody = StringBuilder()
        report.results.sortedByDescending { it.durationMs }.map {
            tbody.append(
                """<tr><td>${escapeHtml4(it.testName)}</td>
                   <td>${it.className}</td>
                   <td>${it.resultType}</td>
                   <td>${it.durationMs}</td></tr>"""
            )
        }
        return """<table id="table">
            <thead>
                <tr>
                    <td>Test name</td>
                    <td>Class name</td>
                    <td>Result</td>
                    <td>Duration (ms)</td>
                </tr>
            </thead>
            <tbody>
                $tbody
            </tbody>
            </table>"""
    }
}
