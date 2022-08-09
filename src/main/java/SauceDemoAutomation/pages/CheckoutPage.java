package SauceDemoAutomation.pages;

import SauceDemoAutomation.util.CommonUtilities;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static SauceDemoAutomation.util.CommonUtilities.takeSnapShot;

public class CheckoutPage {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        System.out.println("Checkout Page");

        driver.findElement(By.xpath("//button[@id='checkout']")).click();
        CommonUtilities.sleep(3000);
        try{
            driver.getPageSource().contains("YOUR INFORMATION");
            test.log(LogStatus.PASS,"Your information text is present");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Your information text is not present");
            takeSnapShot(driver,"Your information.png");
        }

        driver.findElement(By.cssSelector("#first-name")).sendKeys("ABC");
        driver.findElement(By.cssSelector("#last-name")).sendKeys("XYZ");
        driver.findElement(By.cssSelector("#postal-code")).sendKeys("123456");
        CommonUtilities.sleep(3000);
        test.log(LogStatus.PASS,"Able to insert shipping details");
        driver.findElement(By.cssSelector("[id='continue']")).click();
        CommonUtilities.sleep(3000);
        try{
            driver.getPageSource().contains("CHECKOUT");
            test.log(LogStatus.PASS,"Checkout text is present");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Checkout text is not present");
            takeSnapShot(driver,"Checkout.png");
        }
        driver.findElement(By.cssSelector("#finish")).click();
        try{
            driver.getPageSource().contains("CHECKOUT: COMPLETE!");
            test.log(LogStatus.PASS,"Checkout Completed");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Checkout run into problems");
            takeSnapShot(driver,"Checkout.png");
        }
        CommonUtilities.sleep(3000);
    }
}
