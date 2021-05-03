package tests.task40;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VerifyingAlert {
    private static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        driver.get("https://www.seleniumeasy.com/test/javascript-alert-box-demo.html");
    }

    @DisplayName("Confirm Alert Box")
    @Test
    public void confirmAlertBox(){

        WebElement button = driver.findElement(By.xpath("//button[@onclick='myAlertFunction()']"));
        button.click();
        Alert alert = driver.switchTo().alert();
        String textOnAlert = alert.getText();
        alert.accept();

        Assertions.assertEquals("I am an alert box!", textOnAlert);
    }

    @DisplayName("Confirm Alert Confirm Box ")
    @Test
    public void confirmAlertConfirmBox(){
        WebElement button = driver.findElement(By.xpath("//button[@onclick='myConfirmFunction()']"));
        button.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement confirmMessage = driver.findElement(By.id("confirm-demo"));

        Assertions.assertEquals("You pressed OK!", confirmMessage.getText());
    }

    @DisplayName("Dismiss Alert Confirm Box ")
    @Test
    public void dismissAlertConfirmBox(){
        WebElement button = driver.findElement(By.xpath("//button[@onclick='myConfirmFunction()']"));
        button.click();
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        WebElement confirmMessage = driver.findElement(By.id("confirm-demo"));

        Assertions.assertEquals("You pressed Cancel!", confirmMessage.getText());
    }

    @AfterAll
    public static void cleanup(){
        driver.close();
        driver.quit();
    }
}
