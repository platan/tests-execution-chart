package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import io.github.platan.tests_execution_chart.report.data.Mark
import spock.lang.Specification

import java.time.ZoneOffset

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.SUITE
import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class TestExecutionMermaidDiagramFormatterSpec extends Specification {

    def "should format report with test results"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def reportBuilder = new TestExecutionScheduleReportBuilder()
        reportBuilder.add('Test1', 'test1', 1681402397000, 1681402397100, 'SUCCESS', TEST, 'parent name')
        reportBuilder.add('Test1', 'test2', 1681402397100, 1681402397300, 'SUCCESS', TEST, 'parent name')
        reportBuilder.add('Test1', 'suite', 1681402397000, 1681402397300, 'SUCCESS', SUITE, 'parent name')
        reportBuilder.add('Test2', 'test1', 1681402397000, 1681402397100, 'FAILURE', TEST, 'parent name')
        reportBuilder.add('Test2', 'suite', 1681402397000, 1681402397300, 'FAILURE', SUITE, 'parent name')
        reportBuilder.add('Test3', 'test1', 1681402397100, 1681402397300, 'SKIPPED', TEST, 'parent name')
        reportBuilder.add('Test3', 'suite', 1681402397000, 1681402397300, 'SKIPPED', SUITE, 'parent name')
        def report = reportBuilder.getResults()

        when:
        def mermaidReport = new TestExecutionMermaidDiagramFormatter().format(report)


        def indent = """gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test1
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
test2 - 200 ms :active, 2023-04-13T18:13:17.100+0200, 2023-04-13T18:13:17.300+0200
suite - 300 ms :done, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.300+0200
section Test2
test1 - 100 ms :crit, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
suite - 300 ms :crit,done, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.300+0200
section Test3
test1 - 200 ms :2023-04-13T18:13:17.100+0200, 2023-04-13T18:13:17.300+0200
suite - 300 ms :2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.300+0200
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
        reportBuilder.add('Test1', 'test1', 1681402397000, 1681402397100, 'SUCCESS', TEST, 'parent name')
        reportBuilder.add('Test1', 'test2', 1681402397100, 1681402397300, 'SUCCESS', TEST, 'parent name')
        reportBuilder.add('Test2', 'test1', 1681402397000, 1681402397100, 'SUCCESS', TEST, 'parent name')
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
