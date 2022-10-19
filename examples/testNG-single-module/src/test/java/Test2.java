import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class Test2 {
    @Test(groups = {"group1", "group2"})

    public void test1() throws InterruptedException {
        Thread.sleep(20);
        assertTrue(true);
    }

    @Test(groups = {"ignored"})
    public void test2() throws InterruptedException {
        Thread.sleep(10);
        assertTrue(true);
    }

    @Test(groups = {"group1"})
    public void test3() throws InterruptedException {
        Thread.sleep(30);
        assertTrue(true);
    }
}
