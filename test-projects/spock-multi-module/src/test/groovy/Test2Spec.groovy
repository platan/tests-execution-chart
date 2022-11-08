import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class Test2Spec extends Specification {

    public static final int sleepDuration = 500

    def "test 1"() {
        sleep sleepDuration
        expect:
        true
    }

    @Ignore
    def "test 2"() {
        sleep sleepDuration
        expect:
        true

    }

    @Unroll
    def "test 3 x=#x"() {
        sleep sleepDuration
        expect:
        true
        where:
        x = 2
    }

}