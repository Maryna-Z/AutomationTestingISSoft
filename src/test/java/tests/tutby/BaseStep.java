package tests.tutby;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseStep {
    WebDriver driver;

    @BeforeEach
    public void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
    }

    @AfterEach
    public void destroy(){
        driver.close();
        driver.quit();
    }
}
