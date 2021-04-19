package driver;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxSingleton {
    private static FirefoxDriver instance;

    public static synchronized FirefoxDriver getInstance(){
        if (instance == null) {
            System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
            System.setProperty("webdriver.gecko.driver", "true");
            instance = new FirefoxDriver();
        }
        return instance;
    }
}
