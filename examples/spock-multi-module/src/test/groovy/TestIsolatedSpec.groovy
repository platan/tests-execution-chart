import spock.lang.Isolated
import spock.lang.Specification

@Isolated
class TestIsolatedSpec extends Specification {

    public static final int sleepDuration = 50

    def "test 1"() {
        sleep sleepDuration
        expect:
        true
    }

    def "test 2"() {
        sleep sleepDuration
        expect:
        true
    }

    def "test 3"() {
        sleep sleepDuration
        expect:
        true
    }

}