import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Test(groups = {"parallel"})
public class Test2 {

    private static final int sleepDurationMs = 300;

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
