package tests.tutby;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import web_pages.TutByHomePage;

public class Tests{
    WebDriver driver;
    TutByHomePage tutByHomePage;

    @BeforeEach
    public void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
    }

    @Test
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }

    @AfterEach
    public void destroy(){
        driver.close();
        driver.quit();
    }
}
