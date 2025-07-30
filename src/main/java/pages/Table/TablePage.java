package pages.Table;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TablePage {
	
	
   WebDriver driver;
	public TablePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		}
	
	@FindBy(xpath="//table[@id='table1']")
	WebElement table;
	
	@FindBy(xpath="//table[@id='table1']//tbody//tr")
	List<WebElement> rows;
	
	By header=By.xpath(".//thead//tr//th");
	By row=By.xpath(".//tbody//tr");
	By column=By.xpath(".//tbody//tr//td");
	By tableVisibility=By.cssSelector("table[id='table1']");
			
	
	
	
	
	public void waitForToAppear() {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(tableVisibility));
	}
	public void scrollPageDown() throws InterruptedException {
		
		
	}
	public void goTo() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement link=driver.findElement(By.xpath("//a[text()='Sortable Data Tables']"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", link);
		js.executeScript("arguments[0].style.border='3px solid red'", link);
		Thread.sleep(2000);
		link.click();
		waitForToAppear();
		
		
		
	}
	public int getColumnIndexByHeader(String headerName) {
		int index=1;
		List<WebElement>headerList=table.findElements(header);
		for(WebElement headerElement:headerList) {
			String currentHeader=headerElement.getText().trim();
			if(currentHeader.equalsIgnoreCase(headerName)) {
				return index;
			}
			
			index++;
		}
		throw new RuntimeException("Header '"+headerName+"'not found in table");
		
		
		
	}
	
	
	public int getRowCount() {
		List<WebElement>rowCount=table.findElements(row);
		return rowCount.size();
		
	}
	public int getColumnCount() {
		List<WebElement>columnCount=table.findElements(column);
		return columnCount.size();
		
	}
	
	public String getEmailByName(String expectedName) {
		int nameCol=getColumnIndexByHeader("First Name");
		int emailCol=getColumnIndexByHeader("Email");
		Optional<String> actualEmail =rows.stream().filter(f->f.findElement(By.xpath("./td["+nameCol+"]"))
				.getText().trim().equals(expectedName)).map(f->f.findElement(By.xpath("./td["+emailCol+"]")).getText().trim()).findFirst();
		return actualEmail.get();
		
		
	}
	public Double getHighestDue() {
		int dueCol=getColumnIndexByHeader("Due");
		List<String> dueAmountList=rows.stream().map(s->s.findElement(By.xpath("./td["+dueCol+"]")).getText()).map(s->s.replace("$", " ").trim()).collect(Collectors.toList());
		 Double maxDue=  dueAmountList.stream().mapToDouble(Double::parseDouble).max().getAsDouble();
		 return maxDue;
	}
	
	public void getPersonInfo() {
		int nameCol=getColumnIndexByHeader("First Name");
		int emailCol=getColumnIndexByHeader("Email");
		int dueCol=getColumnIndexByHeader("Due");
		int lastNameCol=getColumnIndexByHeader("Last Name");
		 for(WebElement row:rows) {
			 String duetext=row.findElement(By.xpath("./td["+dueCol+"]")).getText().replace("$", " ").trim();
			 Double due =Double.parseDouble(duetext);
			 
			 
			  if (Double.compare(due, getHighestDue()) == 0) {
				 
				String lastName= row.findElement(By.xpath("./td["+lastNameCol+"]")).getText().trim();
				String firstName= row.findElement(By.xpath("./td["+nameCol+"]")).getText().trim();
				String email= row.findElement(By.xpath("./td["+emailCol+"]")).getText().trim();
				String dueAmount= row.findElement(By.xpath("./td["+dueCol+"]")).getText().trim();
				System.out.println("Details of the person with highest Due");
				System.out.println("First name:"+firstName);
				System.out.println("LastName:"+lastName);
				System.out.println("Email:"+email);
				System.out.println("Due Amount:"+dueAmount);
				
				break;
				
			 }
			 
			 
		 }
	}


	

}
