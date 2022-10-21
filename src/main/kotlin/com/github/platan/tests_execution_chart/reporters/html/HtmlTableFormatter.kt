package com.github.platan.tests_execution_chart.reporters.html

import com.github.platan.tests_execution_chart.TestExecutionScheduleReport

class HtmlTableFormatter {

    fun format(report: TestExecutionScheduleReport): String {
        val tbody = StringBuilder()
        report.results.sortedByDescending { it.durationMs }.map {
            tbody.append(
                """<tr><td>${it.testName}</td>
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
                    <td class="selected order-desc">Duration (ms)</td>
                </tr>
            </thead>
            <tbody>
                $tbody
            </tbody>
            </table>"""
    }
}
