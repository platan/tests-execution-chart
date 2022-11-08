import spock.lang.Ignore
import spock.lang.Specification


class Test1Spec extends Specification {

    private static final int sleepDuration = 200

    def "test 1"() {
        sleep sleepDuration
        expect:
        true
    }

    @Ignore
    def "test 2"() {
        sleep sleepDuration
        expect:
        false
    }

    def "test 3"() {
        sleep sleepDuration
        expect:
        true
    }

}