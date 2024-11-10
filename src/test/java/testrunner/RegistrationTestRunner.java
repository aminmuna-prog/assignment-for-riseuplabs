package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import utils.Utils;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.time.Duration;

public class RegistrationTestRunner extends Setup {
    @Test(priority = 1, description = "User can register by providing all info")
    public void userRegByallFields() throws InterruptedException, IOException, ParseException, ConfigurationException {
        RegistrationPage userReg = new RegistrationPage(driver);
        Faker faker = new Faker();
        userReg.btnRegister.click();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email = "rowshonamin10+71@gmail.com";
        String password = "1234";
        String phonenum = "01505"+ Utils.generateRandomNumber(100000, 999999);
        String address = faker.address().fullAddress();
        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNum(phonenum);
        userModel.setAddress(address);
        userReg.doRegistration(userModel);
        doAssertionReg();
        Thread.sleep(2000);
        assertEmail();
//    save user info as json
        JSONObject userObj = new JSONObject();
        userObj.put("firstName", firstname);
        userObj.put("lastName", lastname);
        userObj.put("email", email);
        userObj.put("password", password);
        userObj.put("phonenum", phonenum);
        userObj.put("address", address);
        Utils.saveUserInfo("./src/test/resources/users.json", userObj);
        Thread.sleep(2000);

    }
    public void doAssertionReg() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));
        String successMessageActual= driver.findElement(By.className("Toastify__toast")).getText();
        String successMessageExpected="successfully";
        System.out.println(successMessageActual);
        Assert.assertTrue(successMessageActual.contains(successMessageExpected));

    }
    public void assertEmail() throws ConfigurationException, IOException, InterruptedException, ConfigurationException {

        Thread.sleep(2000);
        String confirmationEmailActual = Utils.readLatestMail();
        String confirmationEmailExpected = "Dear";
        System.out.println(confirmationEmailActual);
        Assert.assertTrue( confirmationEmailActual.contains(confirmationEmailExpected) );

    }
}
