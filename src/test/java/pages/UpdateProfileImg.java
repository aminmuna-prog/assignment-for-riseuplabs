package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UpdateProfileImg {
    @FindBy(css = "[data-testid = AccountCircleIcon]")
    public WebElement btnProfileIcon;
    @FindBy(css = "[role=menuitem]")
    public List<WebElement> btnProfileMenuItems;
    @FindBy(xpath = "//button[normalize-space()='Edit']")
    public WebElement editBtn;
    @FindBy(xpath = "//button[normalize-space()='Upload Image']")
    public WebElement uploadBtn;
    @FindBy(xpath = "//button[normalize-space()='Update']")
    public WebElement updateBtn;
    @FindBy(name = "phoneNumber")
    public WebElement textPhoneNumber;
    public UpdateProfileImg(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
