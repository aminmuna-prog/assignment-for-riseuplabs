package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.LoginPage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AdminDashboardTestRunner extends Setup {
    @Test(priority = 1, description = "Login with admin creds")
    public void doLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin@test.com", "admin123");
    }
   @Test(priority = 2, description = "Check register user is shown in the userlist")
    public void checkUser() throws IOException, ParseException, InterruptedException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String firstName = (String) userObj.get("firstName");
        System.out.println(firstName);
        String email =(String) userObj.get("email");
//        String phoneNumber = (String) userObj.get("phonenum");
//        get data from dashboard
        List<WebElement> firstRowData = driver.findElements(By.xpath("//tbody/tr[1]/td"));
        String dashboardFirrstname =firstRowData.get(0).getText();
        System.out.println(dashboardFirrstname);
        Assert.assertTrue(dashboardFirrstname.contains(firstName));
        String dashboardEmail =firstRowData.get(2).getText();
        System.out.println(dashboardEmail);
        Assert.assertTrue(dashboardEmail.contains(email));
        Thread.sleep(2000);

    }
    @Test(priority = 3, description = "check if the uploaded image is showing on the user profile")
     public void showImage() throws InterruptedException {
     AdminDashboardPage userImg = new AdminDashboardPage(driver);
     userImg.userViewBtn.click();
     Thread.sleep(2000);
     String src = userImg.profileImage.getAttribute("src");
     assert src != null;
     Assert.assertTrue(src.contains("profileImage"), "profile image was successfully uploaded");

    }
}
