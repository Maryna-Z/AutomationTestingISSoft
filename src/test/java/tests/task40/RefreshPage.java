package tests.task40;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RefreshPage {
    private static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        driver.get("https://www.seleniumeasy.com/test/bootstrap-download-progress-demo.html");
    }

    @DisplayName("Refresh page")
    @Test
    public void refreshPage(){
        driver.findElement(By.id("cricle-btn")).click();
        WebElement percenttext = driver.findElement(By.className("percenttext"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement(percenttext, "50"));
        driver.navigate().refresh();
        WebElement percent = driver.findElement(By.className("percenttext"));

        Assertions.assertEquals(percent.getText(),"0%");
    }

    @AfterAll
    public static void cleanup(){
        driver.close();
        driver.quit();
    }
}
