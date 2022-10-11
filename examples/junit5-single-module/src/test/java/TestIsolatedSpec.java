import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

@Isolated
class TestIsolatedSpec  {

    private static final int sleepDurationMs = 50;

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @Test
    public void test2() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

    @Test
    public void test3() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
    }

}