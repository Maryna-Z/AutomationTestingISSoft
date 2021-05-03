package tests.tutby;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import web_pages.TutByHomePage;

public class Tests{
    WebDriver driver;
    TutByHomePage tutByHomePage;

    @BeforeEach
    public void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.FF);
    }

    @Test
    @DisplayName("Verify Authorization")
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }

    @DisplayName("Verify Authorization parametrized method")
    @ParameterizedTest
    @CsvFileSource(resources = "/credentials.csv", numLinesToSkip = 1)
    public void verifyAuthorizationWithParameters(String loginValue, String passwordValue){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSiteWithParameters(loginValue, passwordValue);
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }

    @AfterEach
    public void destroy(){
        driver.close();
        driver.quit();
    }
}
//src/test/resources/credentials.csv