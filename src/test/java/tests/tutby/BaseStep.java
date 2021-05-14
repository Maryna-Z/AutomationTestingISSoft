package tests.tutby;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

public class BaseStep {
    static WebDriver driver;

    @BeforeAll
    public static void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
    }

    @AfterAll
    public static void destroy(){
        driver.close();
        driver.quit();
    }
}
