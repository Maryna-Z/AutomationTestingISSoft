package web_pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.Utils;

import java.io.File;
import java.io.IOException;
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
        try {
            takeScreenshot("src/test/resources/screen.png");
        } catch (IOException ex) {
            System.out.println("Error to file creating while screening");
        }
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

    private void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }
}
