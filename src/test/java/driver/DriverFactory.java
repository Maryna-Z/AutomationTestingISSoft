package driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    public DriverFactory() {
    }

    public static WebDriver getDriver(Config config) {
        switch(config) {
            case CHROME:
                return ChromeSingleton.getChromeDriver();
            case FF:
                return FirefoxSingleton.getFirefoxDriver();
            default:
                throw null;
        }
    }
}
