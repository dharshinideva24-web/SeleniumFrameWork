package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	

	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts;
	@FindBy(css=".totalRow button")
     WebElement CheckOut;
	
	public Boolean productDisplay(String Productname) {
		Boolean Match = cartProducts.stream().anyMatch(cp->cp.getText().equalsIgnoreCase(Productname));
		return Match;
	}
	
	public CheckOutPage CheckOutClick() {
		 CheckOut .click();
		 CheckOutPage CheckOutPage = new CheckOutPage(driver);
		 return CheckOutPage;
	}
	

}
