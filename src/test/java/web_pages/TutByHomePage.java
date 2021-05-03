package web_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utils.Utils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

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

    public void loginToSiteWithParameters(String loginValue, String passwordValue){
        driver.get("https://www.tut.by");
        try {
            Thread.sleep(2000);//This type of waiter doesn't belong to any type of waiters (Implicit and Explicit)
        } catch (InterruptedException ex) {
            System.out.println("The connection was interrupted");
        }
        authLink.click();
        login.sendKeys(loginValue);
        password.sendKeys(passwordValue);
        enter.click();
    }

    public String extractUserName(){
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(5, SECONDS)
                .pollingEvery(1,MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(userName));
        return userName.getText();
    }
}
