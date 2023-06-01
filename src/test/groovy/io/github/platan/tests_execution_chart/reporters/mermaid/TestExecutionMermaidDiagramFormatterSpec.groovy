package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.Mark
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification

import java.time.ZoneOffset

class TestExecutionMermaidDiagramFormatterSpec extends Specification {

    def "should format report with test results"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def reportBuilder = new TestExecutionScheduleReportBuilder()
        reportBuilder.add('Test1', 'test1', 1681402397000, 1681402397100, 'SUCCESS')
        reportBuilder.add('Test1', 'test2', 1681402397100, 1681402397300, 'SUCCESS')
        reportBuilder.add('Test2', 'test1', 1681402397000, 1681402397100, 'SUCCESS')
        def report = reportBuilder.getResults()

        when:
        def mermaidReport = new TestExecutionMermaidDiagramFormatter().format(report)


        def indent = """gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test1
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
test2 - 200 ms :active, 2023-04-13T18:13:17.100+0200, 2023-04-13T18:13:17.300+0200
section Test2
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
"""
        then:
        mermaidReport == indent

        cleanup:
        TimeZone.setDefault(getDefault)
    }

    def "should format report with test results and marks"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def reportBuilder = new TestExecutionScheduleReportBuilder()
        reportBuilder.add('Test1', 'test1', 1681402397000, 1681402397100, 'SUCCESS')
        reportBuilder.add('Test1', 'test2', 1681402397100, 1681402397300, 'SUCCESS')
        reportBuilder.add('Test2', 'test1', 1681402397000, 1681402397100, 'SUCCESS')
        def results = reportBuilder.getResults()
        def report = results.copy(results.results, [new Mark('mark1', 1681402397400)])

        when:
        def mermaidReport = new TestExecutionMermaidDiagramFormatter().format(report)


        def indent = """gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test1
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
test2 - 200 ms :active, 2023-04-13T18:13:17.100+0200, 2023-04-13T18:13:17.300+0200
section Test2
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
mark1 : milestone, 2023-04-13T18:13:17.400+0200, 0
"""
        then:
        mermaidReport == indent

        cleanup:
        TimeZone.setDefault(getDefault)
    }
}
