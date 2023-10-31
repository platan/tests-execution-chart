package io.github.platan.tests_execution_chart.reporters.json

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import spock.lang.Specification
import spock.lang.Subject

class JsonTestExecutionReportFormatterTest extends Specification {

    @Subject
    def formatter = new JsonTestExecutionReportFormatter()

    def "should format empty report"() {
        given:
        def emptyReport = new TestExecutionScheduleReportBuilder().getResults()

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

}
