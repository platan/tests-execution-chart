package io.github.platan.tests_execution_chart.report

import spock.lang.Specification

import java.time.Instant

class TestExecutionScheduleReportTest extends Specification {

    def "shift timestamps when report has many entries"() {
        given:
        def report = new TestExecutionScheduleReport([
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed'),
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T19:00:03Z'), toEpochMilli('2023-03-10T19:00:08Z'), 'passed'),
        ])

        when:
        def shiftedResult = report.timestampsShiftedToStartOfDay()

        then:
        shiftedResult == new TestExecutionScheduleReport([
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T00:00:00Z'), toEpochMilli('2023-03-10T00:00:03Z'), 'passed'),
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T00:00:01Z'), toEpochMilli('2023-03-10T00:00:06Z'), 'passed'),
        ])
    }

    def "shift timestamps when report has one entry"() {
        given:
        def report = new TestExecutionScheduleReport([
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T19:00:02Z'), toEpochMilli('2023-03-10T19:00:05Z'), 'passed'),
        ])

        when:
        def shiftedResult = report.timestampsShiftedToStartOfDay()

        then:
        shiftedResult == new TestExecutionScheduleReport([
                new TimedTestResult('class', 'test', toEpochMilli('2023-03-10T00:00:00Z'), toEpochMilli('2023-03-10T00:00:03Z'), 'passed'),
        ])
    }

    def "shift timestamps when report has no entry"() {
        given:
        def report = new TestExecutionScheduleReport([])

        when:
        def shiftedResult = report.timestampsShiftedToStartOfDay()

        then:
        shiftedResult == new TestExecutionScheduleReport([])
    }

    private static long toEpochMilli(String instant) {
        Instant.parse(instant).toEpochMilli()
    }
}
