import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Test(groups = {"parallel"})
public class Test1 {

    private static final int sleepDurationMs = 200;

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }

    @Ignore
    @Test
    public void test2() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }

    @Test
    public void test3() throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }
}
