package base.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



public class BaseTest {
	public static WebDriver driver;

	public static WebDriver intializeDriver() throws IOException{
		Properties prop = new Properties();
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")
				+"\\src\\main\\java\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName =System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
		
		//String browserName =prop.getProperty("Browser");
		
		if(browserName.contains("chrome")) {
			
			System.setProperty("WebDriver.chrome.ChromeDriver", "C:\\Users\\manpr\\Downloads\\chromedriver-win64\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("WebDriver.gecko.Driver", "C:\\Users\\manpr\\Downloads\\geckodriver.exe");
			driver= new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			System.setProperty("WebDriver.edge.Driver","C:\\Users\\manpr\\Downloads\\msedgedriver.exe");
			driver=new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
		}
/*	public static WebDriver getDriver() {
		return driver;
		
	}
	public static void quitDriver() {
		if(driver!=null){
			driver.quit();
			driver=null;
		}
	}
	*/
	@BeforeMethod(alwaysRun=true)
	public void launchApplication() throws IOException {
	driver= intializeDriver();
	driver.get("https://the-internet.herokuapp.com");
	
	
	
	}
		
   @AfterMethod(alwaysRun=true)
	public void teardown() {
		driver.close();
   }
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts= (TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		
		File destFile=new File(System.getProperty("user.dir") +"\\report\\" + testCaseName +".png");
		FileUtils.copyFile(source, destFile);
		return System.getProperty("user.dir") +"\\report\\" + testCaseName +".png";
		
		
	}

}
