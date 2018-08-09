package Login;

import BasePackage.BaseClass;
import MainPage.trendyolMainPageImage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static KeywordLayer.Command.*;

public class LoginAction extends BaseClass {

    private static final By loginIconUser = By.className("login-register-button-container");
    private static final By accountLoginButton = By.cssSelector(".login");
    private static final By email = By.id("email");
    private static final By password = By.id("password");
    private static final By loginSubmitButton = By.id("loginSubmit");
    private static final By loggedInPanel = By.cssSelector(".loggedin-panel-container");

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFCell cell;
    private static File src;

    @Test
    public void trendyolLogin() throws Exception {

        src = new File(System.getProperty("user.dir") + "/src/main/resources/DataDriven Excel File/trendyolLoginDataDriven.xlsx");
        FileInputStream finput = new FileInputStream(src);
        workbook = new XSSFWorkbook(finput);
        sheet = workbook.getSheetAt(0);

        GetURL("https://www.trendyol.com/");

        try {
            WaitElementToBeClickable(trendyolMainPageImage.ClosePopup);
            Click(trendyolMainPageImage.ClosePopup);
        } catch (Exception ex) {
        }

        WaitElementVisible(loginIconUser);
        MouseOver(loginIconUser);

        WaitElementToBeClickable(accountLoginButton);
        Click(accountLoginButton);

        cell = sheet.getRow(3).getCell(1);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String emailValue = cell.getStringCellValue();

        cell = sheet.getRow(3).getCell(2);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String passwordValue = cell.getStringCellValue();

        WaitElementVisible(email);
        SendKeys(email, emailValue);
        SendKeys(password, passwordValue);
        Click(loginSubmitButton);
        isElementPresent(loggedInPanel); // Verify Login

    }

    @AfterMethod
    public static void TestResult(ITestResult result) throws Exception {

        if (result.getStatus() == ITestResult.FAILURE) {

            sheet.getRow(3).createCell(3).setCellValue("FAILURE");
            FileOutputStream fileOutput = new FileOutputStream(src);
            workbook.write(fileOutput);

        } else if (result.getStatus() == ITestResult.SKIP) {

            sheet.getRow(3).createCell(3).setCellValue("SKIP");
            FileOutputStream fileOutput = new FileOutputStream(src);
            workbook.write(fileOutput);

        } else if (result.getStatus() == ITestResult.SUCCESS) {

            sheet.getRow(3).createCell(3).setCellValue("SUCCESS");
            FileOutputStream fileOutput = new FileOutputStream(src);
            workbook.write(fileOutput);
        }
    }
}
