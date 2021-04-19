package driver;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxSingleton {
    private static FirefoxSingleton instance;
    private static FirefoxDriver firefoxDriver;

    private FirefoxSingleton(){
        System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", "true");
        firefoxDriver = new FirefoxDriver();
    }

    private static synchronized FirefoxSingleton getInstance(){
        if (instance == null) {
            instance = new FirefoxSingleton();
        }
        return instance;
    }

    public static FirefoxDriver getFirefoxDriver(){
        getInstance();
        return firefoxDriver;
    }
}
