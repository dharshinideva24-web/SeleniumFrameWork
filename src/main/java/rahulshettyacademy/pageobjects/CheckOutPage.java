package rahulshettyacademy.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.AbstractComponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents{

WebDriver driver;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(css="[placeholder='Select Country']")
	WebElement Country;
	
	@FindBy(css=".action__submit")
	WebElement Submit;
	
	@FindBy(xpath="//button[contains(@class,'ta-item')][2]")
	WebElement SelectCountry;
	
	By result = By.cssSelector(".ta-results");
	
	public void SelectCountry(String CountryName) {
		Actions a = new Actions(driver);
	       a.sendKeys(Country, CountryName).build().perform();
	       waitForElementtoAppear(result);
	       SelectCountry.click();
	}
	
	public ConfirmationPage SubmitOrder() {
		Submit.click();
		ConfirmationPage ConfirmationPage = new ConfirmationPage(driver);
		return ConfirmationPage;
	}
	
}
