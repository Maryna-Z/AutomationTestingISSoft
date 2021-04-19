package driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSingleton {
    private static ChromeDriver instance;

    public static synchronized ChromeDriver getInstance(){
        if (instance == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            instance = new ChromeDriver(options);
        }
        return instance;
    }
}
