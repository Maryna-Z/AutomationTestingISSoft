package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Optional;

public class DriverSingleton {
    private static volatile DriverSingleton instance;
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private DriverSingleton(){
    }

    public static DriverSingleton getInstance(){
        DriverSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DriverSingleton.class) {
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DriverSingleton();
                }
            }
        }
        return localInstance;
    }

    public WebDriver getDriver(Config config){
        WebDriver driver = webDriver.get();
        if (driver == null){
            switch(config) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case FF:
                    System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                default:
                    driver = null;
            }
            Optional.ofNullable(driver)
                    .ifPresent(dr -> webDriver.set(dr));
        }
        return driver;
    }
}
