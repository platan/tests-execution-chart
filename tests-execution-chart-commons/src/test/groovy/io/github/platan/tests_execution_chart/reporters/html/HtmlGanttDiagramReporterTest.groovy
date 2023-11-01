package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.TempDir

class HtmlGanttDiagramReporterTest extends Specification {

    @Subject
    def htmlGanttDiagramReporter = new HtmlGanttDiagramReporter()
    def configOutputLocation = "my-output-location"

    def setup() {
        htmlGanttDiagramReporter.setLogger { println(it) }
    }

    @TempDir
    File baseDir

    def "should embed script file"() {
        given:
        def script = new HtmlConfig.Script(HtmlConfig.Script.getSrcDefault(), true, new HtmlConfig.Script.Options(10_000))
        htmlGanttDiagramReporter.setConfiguration(new HtmlConfig(true, configOutputLocation, script))
        def report = new TestExecutionScheduleReportBuilder().build()

        when:
        htmlGanttDiagramReporter.report(report, baseDir, 'my-task')

        then:
        def reportContent = new File(baseDir, "$configOutputLocation/my-task.html").text
        reportContent.contains '<script src="mermaid.min.js"></script>'
    }

    def "should not embed script file"() {
        given:
        def scriptSource = HtmlConfig.Script.getSrcDefault()
        def script = new HtmlConfig.Script(scriptSource, false, new HtmlConfig.Script.Options(10_000))
        htmlGanttDiagramReporter.setConfiguration(new HtmlConfig(true, configOutputLocation, script))
        def report = new TestExecutionScheduleReportBuilder().build()

        when:
        htmlGanttDiagramReporter.report(report, baseDir, 'my-task')

        then:
        def reportContent = new File(baseDir, "$configOutputLocation/my-task.html").text
        reportContent.contains """<script src="$scriptSource"></script>"""
    }
}
