package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;

public class AbstractComponents {
	
	 WebDriver driver;

	public AbstractComponents(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[contains(@routerlink,'cart')]")
	WebElement cartclick;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement orderHeader;
	
	public void waitForElementtoAppear(By findBy) {
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    
    wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementtoAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	    wait.until(ExpectedConditions.visibilityOf(findBy));
	    
	
		}
	
	public void waitForElementtoDisappear(WebElement ele) throws InterruptedException {
		/*WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
		wait.until(ExpectedConditions.invisibilityOf(ele));
		wait.until(ExpectedConditions.invisibilityOf(ele));*/
		Thread.sleep(1000);
	}
	
	public orderPage goToOrder() {
		orderHeader.click();
		orderPage orderPage= new orderPage(driver);
		return orderPage;
	}
	public CartPage goToCart() {
		cartclick.click();
		CartPage CartPage= new CartPage(driver);
		return CartPage;
	}
	
}
