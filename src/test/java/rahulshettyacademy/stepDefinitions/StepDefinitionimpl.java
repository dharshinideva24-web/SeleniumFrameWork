package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class StepDefinitionimpl extends BaseTest{
	 public LandingPage LandingPage;
  public ProductCatalogue ProductCatalogue;
  public ConfirmationPage ConfirmationPage;
   
   
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		
		LandingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
		public void logged_in_username_and_password(String username , String password) 
	{
		 ProductCatalogue =LandingPage.loginApplication(username,password);
		}
	
	@When("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products= ProductCatalogue.getProductList();
	       
	       ProductCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_andsubmit_order(String productName) {
		CartPage CartPage = ProductCatalogue.goToCart();
	      Boolean Match=  CartPage.productDisplay(productName);
	      
	       Assert.assertTrue(Match);
	       CheckOutPage CheckOutPage =CartPage.CheckOutClick();
	       
	       CheckOutPage.SelectCountry("india");
	        ConfirmationPage= CheckOutPage.SubmitOrder();
	       
		}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed(String string) {
		String Msg = ConfirmationPage.getConfirmationMsg();
	       Assert.assertTrue(Msg.equalsIgnoreCase(string));
	}
	
	@Then("{string} message is displayed")
	public void something_message_displayed(String string) {
		
		 Assert.assertEquals(string, LandingPage.getErrorMessage());
	}
	

	@When("I purchase using data set {string}")
	public void purchase_using_dataset(String testId) throws IOException, InterruptedException {
		String path = System.getProperty("user.dir")
				+ "\\src\\test\\java\\rahulshettyacademy\\data\\testdata.xlsx";
		List<HashMap<String, String>> rows =
				new rahulshettyacademy.data.ExcelReader().getExcelData(path, "PurchaseData");

		HashMap<String, String> data = null;
		for (HashMap<String, String> r : rows) {
			if (testId.equalsIgnoreCase(r.get("testId"))) { data = r; break; }
		}
		if (data == null) throw new RuntimeException("No data set found for testId: " + testId);

		ProductCatalogue = LandingPage.loginApplication(data.get("email"), data.get("password"));
		ProductCatalogue.addProductToCart(data.get("Productname"));
		CartPage CartPage = ProductCatalogue.goToCart();
		Assert.assertTrue(CartPage.productDisplay(data.get("Productname")));
		CheckOutPage CheckOutPage = CartPage.CheckOutClick();
		CheckOutPage.SelectCountry("india");
		ConfirmationPage = CheckOutPage.SubmitOrder();
	}

}
