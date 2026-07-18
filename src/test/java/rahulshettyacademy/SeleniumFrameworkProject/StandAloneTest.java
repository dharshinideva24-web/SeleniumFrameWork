package rahulshettyacademy.SeleniumFrameworkProject;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Productname ="ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("dharshinideva24@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Deva@2410");
        driver.findElement(By.id("login")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
       List<WebElement> Products = driver.findElements(By.cssSelector(".mb-3"));
     WebElement Prod =  Products.stream().filter(Product->
       Product.findElement(By.cssSelector("b")).getText().equals(Productname)).findFirst().orElse(null);
       Prod.findElement(By.cssSelector(".mb-3 button:last-of-type")).click();
       
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
       wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
       
       WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
       longWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-spinner-overlay")));
       //wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ngx-spinner-overlay"))));
       //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@routerlink,'cart')]")));
       
       driver.findElement(By.xpath("//button[contains(@routerlink,'cart')]")).click();
       List<WebElement> CartProd = driver.findElements(By.cssSelector(".cartSection h3"));
       Boolean Match = CartProd.stream().anyMatch(cp->cp.getText().equalsIgnoreCase(Productname));
       Assert.assertTrue(Match);
       driver.findElement(By.cssSelector(".totalRow button")).click();
       
       Actions a = new Actions(driver);
       a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
       
       //driver.findElement(By.cssSelector("button.ta-item:last-of-type")).click();
       driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
       driver.findElement(By.cssSelector(".action__submit")).click();
       
       String Msg= driver.findElement(By.cssSelector(".hero-primary")).getText();
       Assert.assertTrue(Msg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
       
       
       
        
        
	}

}
