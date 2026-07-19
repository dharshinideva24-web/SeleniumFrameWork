package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

 public class BaseTest {

	public WebDriver driver;
  public LandingPage LandingPage;
  
	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		prop.load(fis);
	String browserName=	System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		//String browserName = prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) {
				options.addArguments("headless");
				options.addArguments("--window-size=1920,1080");
			}
			
			 driver = new ChromeDriver(options);
			 

		} else if (browserName.equalsIgnoreCase("firefox")) {
			//
		}

		else if (browserName.equalsIgnoreCase("edge")) {
			//
			System.setProperty("WebDriver.edge.driver", "edge.exe");
			driver = new EdgeDriver();
		}

		// Wrap the driver so screenshots are captured automatically on every page load & click
		driver = new EventFiringDecorator<>(new ScreenshotListener()).decorate(driver);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if (!browserName.contains("headless")) {
		    driver.manage().window().maximize();
		}

		
		DriverManager.set(driver);
		return driver;
	}
	
	public List<HashMap<String , String>> getJsonDataToMap(String FilePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(FilePath)
				,StandardCharsets.UTF_8);
		
		//String to hashmap import jackson databind in mvn repo
		ObjectMapper mapper = new ObjectMapper();
		 List<HashMap<String , String>> data = mapper.readValue(jsonContent,new TypeReference<List<HashMap<String, String>>>() {});
         return data;
	}
	
	public String getScreenshot(String testcaseName,WebDriver driver) throws IOException {
		 
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 File file = new File(System.getProperty("user.dir")+ "//reports//" + testcaseName + ".png");
		 FileUtils.copyFile(source, file);
		 return System.getProperty("user.dir")+ "//reports//" + testcaseName + ".png";
		 
	 }


	public void captureStep(String label) {
		try {
			byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			rahulshettyacademy.resources.EvidenceManager.addShot(png, label);
		} catch (Exception e) {
			System.out.println("captureStep skipped: " + e.getMessage());
		}
	}

	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		 LandingPage = new LandingPage(driver);
		LandingPage.goTo();
		return LandingPage;

	}
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}

}
