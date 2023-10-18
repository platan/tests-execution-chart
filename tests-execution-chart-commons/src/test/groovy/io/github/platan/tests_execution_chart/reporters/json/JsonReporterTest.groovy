package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.data.Mark
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.report.data.TimedTestResult
import spock.lang.Specification
import java.time.Instant

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class JsonReporterTest extends Specification {
    def "should generate report in json"() {
        given:
        def pathName = "./test"
        def configOutputLocation = "reports/tests-execution/json"
        def report = new TestExecutionScheduleReport([
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed', TEST)
        ], [new Mark('mark1', toEpochMilli('2023-03-10T19:00:05Z'))])
        def reporter = new JsonReporter().tap {
            it.setConfiguration(new JsonConfig(true, configOutputLocation))
        }

        when:
        reporter.report(report, new File(pathName), "taskname")

        then:
        def file = new File("""${pathName}/${configOutputLocation}/taskname.json""")
        file.getText("UTF-8") ==
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
