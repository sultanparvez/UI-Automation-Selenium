package SauceDemoAutomation.util;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static SauceDemoAutomation.MAIN.dirPath;
public class CommonUtilities {
    public static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            /// throw new Exception("Pause between steps was interrupted", e);
            e.printStackTrace();
        }
    }
    public static void takeSnapShot(WebDriver webdriver , String filename) throws Exception{
//Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
//Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//Move image file to new destination
       File DestFile=new File(dirPath+"\\Screenshots\\"+filename);
//Copy file at destination
       FileUtils.copyFile(SrcFile, DestFile);
    }
    public static void moveFiles(String path, String dir) throws Exception{
        File src = new File(path+"/"+dir);
        String VaultDirectoryName = dirPath+"/"+dir;
        File directory = new File(VaultDirectoryName);
        if (! directory.exists()){
            createDir(dirPath,dir+"Vault");
        }
        File dest = new File(path+"/"+dir+"Vault");

        try {
            FileUtils.copyDirectory(src, dest);
            System.out.println("Moving previous datafiles to vault");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(src);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createDir(String path, String dir) throws Exception{
        String directoryName = dirPath+"\\"+dir;
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("Created New Directory- "+dir);
        }
        else{
            File src = new File(path+"/"+dir);
            String VaultDirectoryName = dirPath+"/"+dir;
            directory = new File(VaultDirectoryName);
            if (! directory.exists()){
                createDir(dirPath,dir+"Vault");
            }
            System.out.println("Created New Directory - "+dir+"Vault");
            File dest = new File(path+"/"+dir+"Vault");

            try {
                FileUtils.copyDirectory(src, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileUtils.cleanDirectory(src);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
