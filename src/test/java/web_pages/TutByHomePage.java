package web_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Utils;

import java.util.Properties;

public class TutByHomePage {
    WebDriver driver;
    private String propertyPath = "src/test/resources/mail.properties";
    private Properties properties = Utils.getProperties(propertyPath);

    @FindBy(css = "div#authorize a")
    private WebElement authLink;

    @FindBy(name = "login")
    private WebElement login;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(xpath = "//input[contains(@class, 'auth__enter')]")
    private WebElement enter;

    @FindBy(className = "uname")
    private WebElement userName;

    @FindBy(css = "div.b-popup-i a.button")
    private WebElement logOutButton;

    @FindBy(css = "a.logedin")
    private WebElement userData;

    public TutByHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void loginToSite(){
        driver.get("https://www.tut.by");
        authLink.click();
        login.sendKeys(properties.getProperty("USER_NAME"));
        password.sendKeys(properties.getProperty("PASSWORD"));
        enter.click();
    }

    public void logOutFromSite(){
        userData.click();
        logOutButton.click();
    }

    public String extractUserName(){
        return userName.getText();
    }

    public Boolean authLinkIsDisplayed(){
        return authLink.isDisplayed();
    }
}
