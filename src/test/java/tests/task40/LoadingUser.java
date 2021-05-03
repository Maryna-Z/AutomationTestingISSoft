package tests.task40;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoadingUser {
    private static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        driver.get("https://www.seleniumeasy.com/test/dynamic-data-loading-demo.html");
    }

    @DisplayName("Loading user")
    @Test
    public void loadingUser() throws InterruptedException {
        driver.findElement(By.id("save")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement userName = driver.findElement(By.xpath("//div[@id='loading']/img"));
        wait.until(ExpectedConditions.elementToBeClickable(userName));

        Assertions.assertTrue(userName.isDisplayed());
    }

    @AfterAll
    public static void cleanup(){
        driver.close();
        driver.quit();
    }
}
