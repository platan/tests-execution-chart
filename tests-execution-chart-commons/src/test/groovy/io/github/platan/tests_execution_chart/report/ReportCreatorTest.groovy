package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.json.JsonConfig
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.TempDir

import java.time.Instant

import static io.github.platan.tests_execution_chart.report.data.TimedTestResult.Type.TEST

class ReportCreatorTest extends Specification {

    @TempDir
    File baseDir
    Logger logger = { String message -> println(message) }
    @Subject
    ReportCreator reportCreator = new ReportCreator(logger)
    def configOutputLocation = "my-output-location"

    def "should create report for given formats"() {
        given:
        def config = new ReportConfig([new JsonConfig(true, configOutputLocation)], new ReportConfig.Marks(), false)
        def report = new TestExecutionScheduleReportBuilder()
                .addResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed', TEST)
                .addMark('mark1', toEpochMilli('2023-03-10T19:00:05Z')).build()

        when:
        reportCreator.createReports(report, config, baseDir, 'my-task')

        then:
        def reportFile = new File(baseDir, "$configOutputLocation/my-task.json")
        reportFile.exists()
    }


    private static long toEpochMilli(String instant) {
        Instant.parse(instant).toEpochMilli()
    }
}
