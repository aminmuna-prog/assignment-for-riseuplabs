package pages;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileReader;
import java.io.IOException;

public class AddItemsPage {
    @FindBy(className ="add-cost-button")
    public WebElement txtaddCostItem;
    @FindBy(className = "submit-button")
    public WebElement txtsubmitBtn;
    @FindBy(id = "itemName")
    public WebElement txtitemName;
    @FindBy(xpath = "//button[normalize-space()='+']")
    public WebElement txtplusBtn;
    @FindBy(id = "amount")
    public WebElement txtamount;
    @FindBy(id = "purchaseDate")
    public WebElement txtdatePicker;
    @FindBy(xpath = "//textarea[@id='remarks']")
    public WebElement txtremarks;
    public AddItemsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void datePickerTest(String purchaseDate){

        String[] dateParts = purchaseDate.split("-");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
        txtdatePicker.sendKeys(day);
        txtdatePicker.sendKeys(month);
        txtdatePicker.sendKeys(Keys.ARROW_RIGHT);
        txtdatePicker.sendKeys(year);
    }
    //    totalamount from csv file
    public int getamount() throws IOException {
        String filePath = "./src/test/resources/items.csv";
        int totalAmount = 0;
        CSVParser csvParser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withFirstRecordAsHeader());
        for(CSVRecord csvRecord: csvParser){
            String Stringamount = csvRecord.get("amount");
            int amount = Integer.parseInt(Stringamount);
            totalAmount += amount;
        }
        return totalAmount;
    }
}
