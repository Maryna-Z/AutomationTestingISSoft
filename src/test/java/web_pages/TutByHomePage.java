package web_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Utils;

import java.util.Properties;

public class TutByHomePage {
    WebDriver driver;
    private String propertyPath = "src/test/resources/mail.properties";
    private Properties properties = Utils.getProperties(propertyPath);

    public TutByHomePage(WebDriver driver){
        this.driver = driver;
    }

    public void loginToSite(){
        driver.get("https://www.tut.by");
        driver.findElement(By.cssSelector("div#authorize a")).click();
        driver.findElement(By.name("login")).sendKeys(properties.getProperty("USER_NAME"));
        driver.findElement(By.name("password")).sendKeys(properties.getProperty("PASSWORD"));
        driver.findElement(By.xpath("//input[contains(@class, 'auth__enter')]")).click();
    }

    public void logOutFromSite(){
        driver.findElement(By.cssSelector("a.logedin")).click();
        driver.findElement(By.cssSelector("div.b-popup-i a.button")).click();
    }

    public String extractUserName(){
        return driver.findElement(By.className("uname")).getText();
    }

    public Boolean authLinkIsDisplayed(){
        return driver.findElement(By.cssSelector("div#authorize a")).isDisplayed();
    }
}
