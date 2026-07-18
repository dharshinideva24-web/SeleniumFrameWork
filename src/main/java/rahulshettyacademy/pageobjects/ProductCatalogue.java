package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
//List<WebElement> Products = driver.findElements(By.cssSelector(".mb-3"));
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	By productBy = By.cssSelector(".mb-3");
	By addcartclick= By.cssSelector(".mb-3 button:last-of-type");
	By toastmsg= By.cssSelector("#toast-container");
	
	@FindBy(css=".ng-animating")
	WebElement animating;
	@FindBy(css=".ngx-spinner-overlay")
	WebElement spinner;
	
	
	public List<WebElement> getProductList() {
		waitForElementtoAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String Productname) {
		 WebElement prod =  getProductList().stream().filter(Product->
	       Product.findElement(By.cssSelector("b")).getText().equals(Productname)).findFirst().orElse(null);
	       return prod;
	}
	
	public void addProductToCart(String Productname) throws InterruptedException {
		WebElement prod = getProductByName(Productname);
		prod.findElement(addcartclick).click();
		waitForElementtoAppear(toastmsg);
		waitForElementtoDisappear(animating);
		waitForElementtoDisappear(spinner);
		
		
	}

}
