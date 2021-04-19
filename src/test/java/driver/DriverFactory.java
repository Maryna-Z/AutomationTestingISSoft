package driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    public DriverFactory() {
    }

    public static WebDriver getDriver(Config config) {
        switch(config) {
            case CHROME:
                return ChromeSingleton.getInstance();
            case FF:
                return FirefoxSingleton.getInstance();
            default:
                throw null;
        }
    }
}
