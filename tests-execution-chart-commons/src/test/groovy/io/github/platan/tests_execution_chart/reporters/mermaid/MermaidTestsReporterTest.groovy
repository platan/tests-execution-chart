package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.TempDir

import java.time.Instant
import java.time.ZoneOffset

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class MermaidTestsReporterTest extends Specification {

    @TempDir
    File baseDir

    def configOutputLocation = "my-output-location"

    @Subject
    def reporter = new MermaidTestsReporter().tap {
        it.setConfiguration(new MermaidConfig(true, configOutputLocation))
    }

    def "should generate report in mermaid format"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def report = new TestExecutionScheduleReportBuilder()
                .addResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed', TEST)
                .addMark('mark1', toEpochMilli('2023-03-10T19:00:05Z')).build()

        when:
        reporter.report(report, baseDir, "taskname")

        then:
        def reportFile = new File(baseDir, "$configOutputLocation/taskname.txt")
        reportFile.text ==
                """|gantt
                   |dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
                   |axisFormat %H:%M:%S.%L
                   |section class
                   |test - 3000 ms :2023-03-10T21:00:02.000+0200, 2023-03-10T21:00:05.000+0200
                   |mark1 : milestone, 2023-03-10T21:00:05.000+0200, 0
                   |""".stripMargin()

        cleanup:
        TimeZone.setDefault(getDefault)
    }

    private static long toEpochMilli(String instant) {
        Instant.parse(instant).toEpochMilli()
    }
}
