package SauceDemoAutomation.util;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static SauceDemoAutomation.MAIN.dataPath;
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
        File src = new File(path+"\\"+dir);
        String VaultDirectoryName = dirPath+"\\"+dir;
        File directory = new File(VaultDirectoryName);
        if (! directory.exists()){
            createDir(dirPath,dir+"Vault");
        }
        File dest = new File(path+"\\"+dir+"Vault");

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
            File src = new File(path+"\\"+dir);
            String VaultDirectoryName = dirPath+"\\"+dir;
            directory = new File(VaultDirectoryName);
            if (! directory.exists()){
                createDir(dirPath,dir+"Vault");
            }
            System.out.println("Created New Directory - "+dir+"Vault");
            File dest = new File(path+"\\"+dir+"Vault");

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
    public static String getFile() throws InterruptedException {
        String dataFile = "";
        File directoryPath = new File(dataPath);
        FilenameFilter xlsxFilefilter = new FilenameFilter(){
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".xlsx")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        String excelFilesList[] = directoryPath.list(xlsxFilefilter);
        for(String fileName : excelFilesList) {
            dataFile = fileName;
            break;
        }
        return "\\"+dataFile;
    }



    public static boolean ValidateDoubleData(String dataFileWithPath,  int sheetNumber, int vRow, int vColumn, double expectedValue) {
        String value = ""; //variable for storing the cell value
        Workbook wbook = null; //initialize Workbook null
        try {

            FileInputStream fis = new FileInputStream(dataFileWithPath);

            wbook = new XSSFWorkbook(fis);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
        Sheet sheet = wbook.getSheetAt((int) sheetNumber-1);
        Row row = sheet.getRow((int) vRow-1);

        Cell cell = row.getCell((int) vColumn-1);
        CellType type = cell.getCellTypeEnum();
        if (type == CellType.STRING) {
            value = cell.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            value = String.valueOf(cell.getNumericCellValue());
        } else if (type == CellType.BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.BLANK) {
            value = "";
        }

        if (cell.getNumericCellValue()==expectedValue){
            return true;
        }
        else {
            return false;
        }
    }

    public static String ValidateDateData(String dataFileWithPath,  int sheetNumber, int vRow, int vColumn) {
        String value = "";
        Workbook wbook = null;
        try {

            FileInputStream fis = new FileInputStream(dataFileWithPath);

            wbook = new XSSFWorkbook(fis);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
        Sheet sheet = wbook.getSheetAt((int) sheetNumber-1);

        Row row = sheet.getRow((int) vRow-1);

        Cell cell = row.getCell((int) vColumn-1);

        if(cell.getCellTypeEnum() == CellType.NUMERIC||cell.getCellTypeEnum() == CellType.FORMULA)
        {
            if(HSSFDateUtil.isCellDateFormatted(cell))
            {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = cell.getDateCellValue();
                value = df.format(date);
            }
        }
        return value;
    }

}
