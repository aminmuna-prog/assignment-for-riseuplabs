package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UpdateProfileImg;

import java.io.FileReader;
import java.io.IOException;

public class UpdateProfileImgTestRunner extends Setup{
    @Test(priority = 1, description = "Login with last user")
    public void doLogin() throws IOException, ParseException {
        LoginPage loginPage = new LoginPage(driver);
//        get last user from json file
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email =(String) userObj.get("email");
        String password = (String) userObj.get("password");
        loginPage.doLogin(email, password);
    }
    @Test(priority = 2, description = "update user profile image")
    public void updateImg() throws InterruptedException {
        UpdateProfileImg userImg = new UpdateProfileImg(driver);
        userImg.btnProfileIcon.click();
        userImg.btnProfileMenuItems.get(0).click();
        userImg.editBtn.click();
        String relativePath ="\\src\\test\\resources\\banner.jpg";
        String absolutePath = System.getProperty("user.dir") + relativePath;
        driver.findElement(By.className("upload-input")).sendKeys(absolutePath);
        userImg.uploadBtn.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        userImg.updateBtn.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

    }
    @Test(priority = 3, description = "update user phone number")
    public void updateUserPhoneNumber() throws InterruptedException {
        UpdateProfileImg userPhonenumber = new UpdateProfileImg(driver);
        userPhonenumber.btnProfileIcon.click();
        userPhonenumber.btnProfileMenuItems.get(0).click();
        userPhonenumber.editBtn.click();
        userPhonenumber.textPhoneNumber.sendKeys(Keys.CONTROL, "a");
        userPhonenumber.textPhoneNumber.sendKeys(Keys.BACK_SPACE);
        userPhonenumber.textPhoneNumber.sendKeys("01684266909");
        userPhonenumber.updateBtn.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

}
