package tests.task40;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SelectingMultipleItems {
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
    }

    @DisplayName("Multiple Selection")
    @Test
    public void selectMultiItem() throws InterruptedException {
        driver.get("https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html");
        Actions builder = new Actions(driver);
        WebElement select = driver.findElement(By.id("multi-select"));
        List<WebElement> options = select.findElements(By.tagName("option"));
        Action multipleSelect = builder.keyDown(Keys.CONTROL)
                .click(options.get(0))
                .click(options.get(3))
                .click(options.get(4))
                .build();
        multipleSelect.perform();

        List<String> cities = new ArrayList<>();
        for(int i = 0; i < options.size()-1; i++){
            if (options.get(i).isSelected()){
                cities.add(options.get(i).getText());
            }
        }

        Assertions.assertAll(
                () -> assertEquals(options.get(0).getText(), cities.get(0)),
                () -> assertEquals(options.get(3).getText(), cities.get(1)),
                () -> assertEquals(options.get(4).getText(), cities.get(2))
        );
    }

    @AfterEach
    public void cleanup(){
       driver.close();
       driver.quit();
    }
}
