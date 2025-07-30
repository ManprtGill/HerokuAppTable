package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class RawCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("WebDriver.chrome.ChromeDriver", "C:\\Users\\manpr\\Downloads\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.navigate().to("https://the-internet.herokuapp.com/tables");
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table[id='table1']")));
		//testCase 2
		int expectedRowCount=4;
		int expectedColumnCount=24;
		String headerName="First Name";
		List<WebElement> rows=driver.findElements(By.xpath("//table[@id='table1']//tbody//tr"));
		
	    int actualRowCount=rows.size();
		System.out.println("Row Count:"+actualRowCount);
		
		int actualColumnCount=driver.findElements(By.cssSelector("table[id='table1'] td")).size();
		System.out.println("Column Count:"+actualColumnCount);
		Assert.assertEquals(expectedRowCount, actualRowCount);
		Assert.assertEquals(expectedColumnCount, actualColumnCount);
		
		
		// TestCase 1 
		List<String> expectedHeader=Arrays.asList("Last Name","First Name","Email","Due","Web Site","Action");
		int index =1;
		List<WebElement> headerList=driver.findElements(By.cssSelector("table[id='table1'] th"));
        List<String> actualHeader= new ArrayList<>();
		for(WebElement headerElement:headerList) {
			String currentHeader=headerElement.getText().trim();
			if(currentHeader.equalsIgnoreCase(headerName)) {
				System.out.println(index);
			}
			index++;
			
			//actualHeader.add(headerElement.getText().trim());
			
			
			}
		Assert.assertEquals(actualHeader, expectedHeader, "Table headers do not match");
		
		//TestCase 3
		String expectedEmail="fbach@yahoo.com";
		
		
		Optional<String> actualEmail =rows.stream().filter(f->f.findElement(By.xpath("./td[2]"))
						.getText().trim().equals("Frank")).map(f->f.findElement(By.xpath("./td[3]")).getText().trim()).findFirst();
		// to retrieve optional string list value '.get()' 
						
	   Assert.assertEquals(actualEmail.get(), expectedEmail,"Email does not match");
		
		// TestCase 4
		//step 1 locate all rows
	
		//converted text into integer using integer.parseInt() List<Double> dueAmountList
	 //  double highestDue = dueList.stream().mapToDouble(Double::parseDouble).max().getAsDouble();
	   //mapToDouble(Double::parseDouble)
	   List<String> dueAmountList=rows.stream().map(s->s.findElement(By.xpath("./td[4]")).getText()).map(s->s.replace("$", " ").trim()).collect(Collectors.toList());
	 Double maxDue=  dueAmountList.stream().mapToDouble(Double::parseDouble).max().getAsDouble();

	 System.out.println(maxDue);
	
	 
	 for(WebElement row:rows) {
		 String duetext=row.findElement(By.xpath("./td[4]")).getText().replace("$", " ").trim();
		 Double due =Double.parseDouble(duetext);
		 System.out.println(due);
		 
		 
		  if (Double.compare(due, maxDue) == 0) {
			 
			String lastName= row.findElement(By.xpath("./td[1]")).getText().trim();
			String firstName= row.findElement(By.xpath("./td[2]")).getText().trim();
			String email= row.findElement(By.xpath("./td[3]")).getText().trim();
			String dueAmount= row.findElement(By.xpath("./td[4]")).getText().trim();
			System.out.println("First name:"+firstName);
			System.out.println("LastName:"+lastName);
			System.out.println("Email:"+email);
			System.out.println("Due Amount:"+dueAmount);
			
			break;
			
		 }
		 
		 
	 }
	 
	
				
	
		
		
		
		
		
		driver.close();		


	}

}
