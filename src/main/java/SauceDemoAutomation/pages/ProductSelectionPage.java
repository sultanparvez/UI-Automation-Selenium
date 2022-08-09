package SauceDemoAutomation.pages;

import SauceDemoAutomation.util.CommonUtilities;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static SauceDemoAutomation.util.CommonUtilities.takeSnapShot;

public class ProductSelectionPage {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        System.out.println("Products selection Page");

        try{
            driver.getPageSource().contains("PRODUCTS");
            test.log(LogStatus.PASS,"Products text is present");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Products text is not present");
            takeSnapShot(driver,"Products.png");
        }
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-fleece-jacket']")).click();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-onesie']")).click();
        CommonUtilities.sleep(4000);
        driver.findElement(By.xpath("//button[@id='remove-sauce-labs-onesie']")).click();
        try{
            driver.findElement(By.cssSelector("button[name='remove-sauce-labs-backpack']")).isEnabled();
            test.log(LogStatus.PASS,"Removed button is enabled");
        } catch(Exception e) {
            test.log(LogStatus.FAIL,"Removed button is disabled");
            takeSnapShot(driver,"remove button.png");
        }
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();


        CommonUtilities.sleep(2000);
    }
}
