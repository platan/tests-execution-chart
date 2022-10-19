import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class Test1 {
    @Test(groups = {"group1", "ignored"})

    public void test1() throws InterruptedException {
        Thread.sleep(40);
        assertTrue(true);
    }

    @Test(groups = {"group1"})
    public void test2() throws InterruptedException {
        Thread.sleep(25);
        assertTrue(true);
    }

    @Test(groups = {"ignored"})
    public void test3() throws InterruptedException {
        Thread.sleep(25);
        assertTrue(true);
    }
}
