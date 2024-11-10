package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminDashboardPage {
    @FindBy(xpath = "//tbody/tr[1]/td[8]/button[1]")
    public WebElement userViewBtn;
    @FindBy(className = "profile-image")
    public WebElement profileImage;
    public AdminDashboardPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
