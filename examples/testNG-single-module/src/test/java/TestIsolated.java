import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Test(groups = {"isolated"})
public class TestIsolated {

    private static final int sleepDurationMs = 100;

    public void test1() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }

    public void test2() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }

    public void test3() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }
}
