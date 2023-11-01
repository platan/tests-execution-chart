package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class JsonTestExecutionReportFormatterTest extends Specification {

    @Subject
    def formatter = new JsonTestExecutionReportFormatter()

    def "should format empty report"() {
        given:
        def emptyReport = new TestExecutionScheduleReportBuilder().build()

        when:
        def result = formatter.format(emptyReport)

        then:
        result ==
                """{
                |    "results": [
                |    ],
                |    "marks": [
                |    ]
                |}""".stripMargin()
    }

    def "should format report with one entry and one mark"() {
        given:
        def reportBuilder = new TestExecutionScheduleReportBuilder()
                .addResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed', TEST)
                .addMark('mark1', toEpochMilli('2023-03-10T19:00:05Z'))
        def report = reportBuilder.build()

        when:
        def result = formatter.format(report)

        then:
        result ==
                """{
                |    "results": [
                |        {
                |            "className": "class",
                |            "testName": "test",
                |            "startTime": 1678474802000,
                |            "endTime": 1678474805000,
                |            "resultType": "passed",
                |            "type": "TEST"
                |        }
                |    ],
                |    "marks": [
                |        {
                |            "name": "mark1",
                |            "timestamp": 1678474805000
                |        }
                |    ]
                |}""".stripMargin()
    }

    private static long toEpochMilli(String instant) {
        Instant.parse(instant).toEpochMilli()
    }

}
