import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Test(groups = {"parallel"})
public class Test2 {

    private static final int sleepDurationMs = 300;

    @DataProvider(name = "data-provider", parallel = true)
    public static Object[][] createData() {
        return new Object[][]{{1}, {2}};
    }


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

    @Test(dataProvider = "data-provider")
    public void test3(int x) throws InterruptedException {
        Thread.sleep(sleepDurationMs);
        assertTrue(true);
    }
}
