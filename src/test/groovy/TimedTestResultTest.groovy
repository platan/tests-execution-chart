import io.github.platan.tests_execution_chart.report.TimedTestResult
import spock.lang.Specification

import static java.time.Duration.ofMillis
import static java.time.Duration.ofSeconds

class TimedTestResultTest extends Specification {

    def "shift timestamps"() {
        given:
        def result = new TimedTestResult(
                'class',
                'test',
                givenStart,
                givenEnd,
                'passed'
        )

        when:
        def shiftedResult = result.shiftTimestamps(duration)

        then:
        shiftedResult.className == 'class'
        shiftedResult.testName == 'test'
        shiftedResult.startTime == exectedStart
        shiftedResult.endTime == expectedEnd
        shiftedResult.resultType == 'passed'

        where:
        givenStart    | givenEnd      | duration      || exectedStart  | expectedEnd
        1196712906001 | 1196712908001 | ofSeconds(4)  || 1196712902001 | 1196712904001
        5000          | 6000          | ofSeconds(4)  || 1000          | 2000
        2000          | 3000          | ofSeconds(4)  || -2000         | -1000
        2000          | 3000          | ofSeconds(0)  || 2000          | 3000
        2000          | 3000          | ofSeconds(-1) || 3000          | 4000
        2000          | 3000          | ofMillis(4)   || 1996          | 2996
    }
}
