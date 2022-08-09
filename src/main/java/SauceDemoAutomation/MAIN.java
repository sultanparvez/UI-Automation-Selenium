package SauceDemoAutomation;
import SauceDemoAutomation.pages.CheckoutPage;
import SauceDemoAutomation.pages.ProductSelectionPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import SauceDemoAutomation.driver.DriverManager;
import SauceDemoAutomation.pages.LoginPage;
import SauceDemoAutomation.util.*;
import org.openqa.selenium.WebDriver;
import java.io.File;
public class MAIN {
    public static ExtentReports extent;
    public static ExtentTest test;
    public static final String dirPath = new File("").getAbsolutePath();
    public static final String dataPath = dirPath+"\\"+"Data";
    public static void main(String[] args) throws Exception {
        //Start Reporter
        AutomationReporter.initializeReporter();
        //Driver Instance
        WebDriver driver = DriverManager.getDriver(false);//flag is for switch between headless and headed
        //Login and Product Selection
       AutomationReporter.startReporter("Login Page");
       LoginPage.execute(driver,test);
        AutomationReporter.endReporter();
        AutomationReporter.startReporter("Product Selection");
        ProductSelectionPage.execute(driver,test);
        AutomationReporter.endReporter();
        AutomationReporter.startReporter("Product Selection");
        CheckoutPage.execute(driver,test);
        AutomationReporter.endReporter();
        AutomationReporter.closeReporter();
        System.out.println("Finished Testing");
        driver.close();
    }
}