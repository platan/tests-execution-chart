import spock.lang.Ignore
import spock.lang.Specification


class MySpec extends Specification {

    public static final int sleepDurationMs = 10

    def "test 1"() {
        expect:
        true
    }

    @Ignore
    def "test 2"() {
        sleep sleepDurationMs
        expect:
        false
    }

    def "test 3"() {
        sleep sleepDurationMs
        expect:
        true
    }

}