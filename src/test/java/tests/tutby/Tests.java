package tests.tutby;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web_pages.TutByHomePage;

public class Tests extends BaseStep{
    private TutByHomePage tutByHomePage;

    @Test
    public void verifyAuthorization(){
        tutByHomePage = new TutByHomePage(driver);
        tutByHomePage.loginToSite();
        Assertions.assertEquals(tutByHomePage.extractUserName(), "Selenium Test", "User is authorized");
    }
}
