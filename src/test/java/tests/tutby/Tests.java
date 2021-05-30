package tests.tutby;

import org.junit.jupiter.api.*;
import web_pages.TutByHomePage;

public class Tests extends BaseStep{
    private TutByHomePage tutByHomePage;

    @Test
    @DisplayName("User is authorized")
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }

    @Test
    @DisplayName("User is logged out")
    public void verifyLoginOut(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        tutByHomePage.logOutFromSite();
        Assertions.assertTrue(tutByHomePage.authLinkIsDisplayed());
    }
}
