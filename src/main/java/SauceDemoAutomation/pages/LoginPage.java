package SauceDemoAutomation.pages;

import SauceDemoAutomation.util.AssertionUtility;
import SauceDemoAutomation.util.CommonUtilities;
import SauceDemoAutomation.util.JsonReader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

import java.util.HashMap;

import static SauceDemoAutomation.util.CommonUtilities.takeSnapShot;

public class LoginPage {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        Assertion hardAssert = AssertionUtility.initializeHardAsserts();
        System.out.println("login Page");
        HashMap<String,String> loginData = JsonReader.configReader("Credentials.json");
        driver.navigate().to(loginData.get("environment"));
        test.log(LogStatus.PASS,"Able to navigate to login page");
        CommonUtilities.sleep(3000);
        try{
            hardAssert.assertEquals(driver.getTitle(), "Swag Labs");
            test.log(LogStatus.PASS,"Login page title matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Title did not matched");
        }
        driver.findElement(By.cssSelector("#user-name")).sendKeys(loginData.get("userName"));
        driver.findElement(By.cssSelector("#password")).sendKeys(loginData.get("password"));
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        test.log(LogStatus.PASS,"Login Successful");
        try{
            driver.findElement(By.xpath("//div[@class='app_logo']")).isDisplayed();
            test.log(LogStatus.PASS,"Homepage header is visible");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Homepage header is not visible");
            takeSnapShot(driver,"Homepage.png");
        }
        try{
            driver.findElement(By.xpath("//div[@class='app_logo2']")).isDisplayed();
            test.log(LogStatus.PASS,"Homepage header is visible");
        } catch(Exception e) {
            test.log(LogStatus.FAIL,"This was failed intentionally ");
            takeSnapShot(driver,"Homepage Intentional Fail.png");
        }
        CommonUtilities.sleep(2000);
    }
}
