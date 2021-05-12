package tests.tutby;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import web_pages.TutByHomePage;

public class Tests extends BaseStep{
    private TutByHomePage tutByHomePage;

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
}
//src/test/resources/credentials.csv