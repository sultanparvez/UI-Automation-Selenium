package SauceDemoAutomation.driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import static SauceDemoAutomation.MAIN.dirPath;
import static SauceDemoAutomation.util.CommonUtilities.createDir;

public class DriverManager {
    public static   WebDriver getDriver(boolean flag) throws Exception {
        createDir(dirPath,"Screenshots");
        System.setProperty("webdriver.chrome.driver", dirPath +"\\Drivers\\chromedriver.exe");
        WebDriver driver = chromeDriver(flag);
        return driver;
    }
    private static WebDriver chromeDriver(boolean flag) throws Exception {
        ChromeOptions options = defaultChromeOptions(flag);

        return new ChromeDriver(options);
    }

    private static ChromeOptions defaultChromeOptions(boolean flag) throws Exception {
        String downloadFilepath = dirPath+"\\Data";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--log-level=OFF");
        options.setExperimentalOption("prefs", chromePrefs);
        if (flag){
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
            System.out.println("Running Headless");
        }

        return options;
    }



}
