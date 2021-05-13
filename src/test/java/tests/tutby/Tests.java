package tests.tutby;

import org.junit.jupiter.api.*;
import web_pages.TutByHomePage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests extends BaseStep{
    private TutByHomePage tutByHomePage;

    @Test
    @DisplayName("User is authorized")
    @Order(1)
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }

    @Test
    @DisplayName("User is logged out")
    @Order(2)
    public void verifyLoginOut(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.logOutFromSite();
        Assertions.assertTrue(tutByHomePage.authLinkIsDisplayed());
    }
}
