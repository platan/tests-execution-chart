package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification

import java.time.ZoneOffset

class TestExecutionMermaidDiagramFormatterSpec extends Specification {

    def "should format report"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def reportBuilder = new TestExecutionScheduleReportBuilder()
        reportBuilder.add('Test1', 'test1', 1681402397000, 1681402397100, 'SUCCESS')
        reportBuilder.add('Test1', 'test2', 1681402397100, 1681402397300, 'SUCCESS')
        reportBuilder.add('Test2', 'test1', 1681402397000, 1681402397100, 'SUCCESS')

        when:
        def mermaidReport = new TestExecutionMermaidDiagramFormatter().format(reportBuilder.getResults())


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
}
