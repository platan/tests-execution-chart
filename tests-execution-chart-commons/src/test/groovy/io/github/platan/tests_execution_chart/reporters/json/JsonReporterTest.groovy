package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification
import spock.lang.TempDir

import java.time.Instant

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class JsonReporterTest extends Specification {

    @TempDir
    File baseDir

    def "should generate report in json"() {
        given:
        def configOutputLocation = "my-output-location"
        def report = new TestExecutionScheduleReportBuilder()
                .addResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed', TEST)
                .addMark('mark1', toEpochMilli('2023-03-10T19:00:05Z')).build()
        def reporter = new JsonReporter().tap {
            it.setConfiguration(new JsonConfig(true, configOutputLocation))
        }

        when:
        reporter.report(report, baseDir, "taskname")

        then:
        def reportFile = new File(baseDir, "$configOutputLocation/taskname.json")
        reportFile.text ==
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
