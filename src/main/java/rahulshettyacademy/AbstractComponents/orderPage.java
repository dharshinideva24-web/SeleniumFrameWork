package rahulshettyacademy.AbstractComponents;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class orderPage extends AbstractComponents{

WebDriver driver;
	
	public orderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> OrderProducts;
	
	
	public Boolean verifyOrderDisplay(String Productname) {
		Boolean Match = OrderProducts.stream().anyMatch(cp->cp.getText().equalsIgnoreCase(Productname));
		return Match;
	}
	
	
}