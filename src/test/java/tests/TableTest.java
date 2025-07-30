package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.Table.BaseTest;
import listeners.MyListeners;
import pages.Table.TablePage;
import resources.ExcelUtils;


 public class TableTest extends BaseTest{
	
	@Test(dataProvider="inputData")
	public void tableTests(String expectedRowCount,String expectedEmail,String expectedColumnCount,String expectedName) throws IOException, InterruptedException {
	Assert.assertNotNull(MyListeners.test,"ExtentTest is not initialized");
	MyListeners.test.log(Status.INFO, "Testing login with user:"+expectedName);
	TablePage table=new TablePage(driver);
	table.goTo();
	Thread.sleep(2000);
	Assert.assertEquals(expectedRowCount, table.getRowCount());
	Assert.assertEquals(expectedColumnCount, table.getColumnCount());
	Assert.assertEquals(table.getEmailByName(expectedName), expectedEmail,"Email does not match");
	table.getPersonInfo();
	

	}

	@DataProvider(name="inputData")
	public Object[][] getDataFromExcel() throws IOException{
		
	return new Object[][] {{"4","fbach@yahoo.com","24","Frank"},{"4","jsmith@gmail.com","24","John"}};
	
		
	}
	

}
