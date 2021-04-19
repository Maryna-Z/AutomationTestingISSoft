package tests.tutby;

import driver.Config;
import driver.DriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import web_pages.TutByHomePage;

public class Tests{
    static WebDriver driver;
    TutByHomePage tutByHomePage;

    @BeforeAll
    public static void initDriver(){
        driver = DriverFactory.getDriver(Config.CHROME);
    }

    @Test
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertTrue(tutByHomePage.extractUserName().equals("Selenium Test"), "User is authorized");
    }

    @AfterAll
    public static void destroy(){
        driver.close();
        driver.quit();
    }
}
