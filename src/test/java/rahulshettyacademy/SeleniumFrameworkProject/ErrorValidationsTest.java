package rahulshettyacademy.SeleniumFrameworkProject;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.TestComponents.Retry;


import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	 @Test(groups = {"Purchase"},retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
      LandingPage.loginApplication("dharshinidega24@gmail.com", "Dev9@2410");
      AssertJUnit.assertEquals("Incorrect email or password.", LandingPage.getErrorMessage());
       
        }
	 
	 @Test
	 public void ProductErrorValidation() throws InterruptedException, IOException {
			// TODO Auto-generated method stub
			String Productname ="ZARA COAT 3";
			String CountryName ="india";
	       ProductCatalogue ProductCatalogue =LandingPage.loginApplication("dharshinideva24@gmail.com", "Deva@2410");
	      List<WebElement> products= ProductCatalogue.getProductList();
	       
	       ProductCatalogue.addProductToCart(Productname);
	       CartPage CartPage = ProductCatalogue.goToCart();
	      
	      Boolean Match=  CartPage.productDisplay("Zara Coat 33");
	      
	       Assert.assertFalse(Match);
	       
	       
	    }

}
