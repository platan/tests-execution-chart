import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Test1Test {

    private static final int sleepDurationMs = 200;

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @Test
    @Disabled
    public void test2() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @Test
    public void test3() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

}