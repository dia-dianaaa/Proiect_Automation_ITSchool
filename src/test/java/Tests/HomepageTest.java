package Tests;

import SharedData.Browser.ShareDataDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomepageTest extends ShareDataDriver {

    @Test
    public void homepageLoads() {
        String title = getDriver().getTitle();
        Assert.assertNotNull(title);
        Assert.assertFalse(title.isBlank(), "Page title should not be empty");
    }
}
