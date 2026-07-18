package rahulshettyacademy.SeleniumFrameworkProject;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.AbstractComponents.orderPage;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class FinalStandaloneTest extends BaseTest {
	String Productname ="ZARA COAT 3";
	String CountryName ="india";
	 @Test(dataProvider="getData" , groups = {"Purchase"})
	public void FinalStandalone( HashMap<String,String> input ) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
       ProductCatalogue ProductCatalogue =LandingPage.loginApplication(input.get("email"),input.get("password"));
      List<WebElement> products= ProductCatalogue.getProductList();
       
       ProductCatalogue.addProductToCart(input.get("Productname"));
       CartPage CartPage = ProductCatalogue.goToCart();
      
      Boolean Match=  CartPage.productDisplay(input.get("Productname"));
      
       Assert.assertTrue(Match);
       CheckOutPage CheckOutPage =CartPage.CheckOutClick();
       
       CheckOutPage.SelectCountry(CountryName);
       ConfirmationPage ConfirmationPage= CheckOutPage.SubmitOrder();
       
      String Msg = ConfirmationPage.getConfirmationMsg();
       Assert.assertTrue(Msg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
       
       
       }
	 
	 @Test(dependsOnMethods= {"FinalStandalone"})
	 public void OrderHistoryTest() {
		 ProductCatalogue ProductCatalogue =LandingPage.loginApplication("dharshinideva24@gmail.com", "Deva@2410");
		 orderPage orderPage=ProductCatalogue.goToOrder();
		 Assert.assertTrue(orderPage.verifyOrderDisplay(Productname));
	 }
	 
	
	 
	 @DataProvider
	 public Object[] [] getData() throws IOException{
		 List<HashMap<String,String>> data = new rahulshettyacademy.data.ExcelReader()
				.getExcelData(System.getProperty("user.dir")
					+"\\src\\test\\java\\rahulshettyacademy\\data\\testdata.xlsx", "PurchaseData");
		 Object[][] arr = new Object[data.size()][1];
		 for (int i = 0; i < data.size(); i++) { arr[i][0] = data.get(i); }
		 return arr;
	 }

}

/*HashMap<String,String>  map = new HashMap<String,String>();
		 map.put("email","dharshinideva24@gmail.com" );
		 map.put("password", "Deva@2410");
		 map.put("Productname", "ZARA COAT 3");
		 HashMap<String,String>  map1 = new HashMap<String,String>();
		 map.put("email","shetty@gmail.com" );
		 map.put("password", "Deva@2410");
		 map.put("Productname", "ADIDAS ORIGINAL");*/
