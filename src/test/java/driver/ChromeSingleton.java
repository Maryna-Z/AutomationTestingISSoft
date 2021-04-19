package driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSingleton {
    private static ChromeSingleton instance;
    private static ChromeDriver chromeDriver;

    private ChromeSingleton(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        chromeDriver = new ChromeDriver(options);
    }
    private static synchronized ChromeSingleton getInstance(){
        if (instance == null) {
            instance = new ChromeSingleton();
        }
        return instance;
    }

    public static ChromeDriver getChromeDriver(){
        getInstance();
        return chromeDriver;
    }
}
