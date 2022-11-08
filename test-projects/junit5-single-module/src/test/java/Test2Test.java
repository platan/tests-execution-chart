import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Test2Test {

    private static final int sleepDurationMs = 500;

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @Test
    @Disabled
    public void test2() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "b"})
    public void test3() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

}