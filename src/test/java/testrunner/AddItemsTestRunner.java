package testrunner;

import config.ItemDataSet;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddItemsPage;
import pages.LoginPage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AddItemsTestRunner extends Setup {
    @Test(priority = 1, description = "Login by the last registered user")
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
    @Test(priority = 2, dataProvider = "itemCSVData", dataProviderClass = ItemDataSet.class, description = "Added items from csv file")
    public void addCost(String itemname, int quantity, String amount, String purchasedate, String month, String remark) throws InterruptedException {
        AddItemsPage addItemsPage = new AddItemsPage(driver);
        addItemsPage.txtaddCostItem.click();
        addItemsPage.txtitemName.sendKeys(itemname);
        for(int i = 1; i <=quantity; i++){
            addItemsPage.txtplusBtn.click();
        }
        addItemsPage.txtamount.sendKeys(amount);
        Thread.sleep(1000);
        addItemsPage.datePickerTest(purchasedate);
        Select dropMonth = new Select(driver.findElement(By.id("month")));
        dropMonth.selectByVisibleText(month);
        addItemsPage.txtremarks.sendKeys(remark);
        addItemsPage.txtsubmitBtn.click();
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

    }
    @Test(priority = 3, description = "Check expected total cost with actual total cost")
    public void totalcost() throws IOException {
        AddItemsPage addCostPage = new AddItemsPage(driver);
        int expectedCost = addCostPage.getamount();
        List<WebElement> summary =driver.findElements(By.xpath("//div[@class='summary']/span"));
        String totalCost = summary.get(1).getText();
        String amount = totalCost.replaceAll("[^0-9]", "");
        int totalCostActual = Integer.parseInt(amount);
        Assert.assertEquals(totalCostActual, expectedCost);

    }

}
