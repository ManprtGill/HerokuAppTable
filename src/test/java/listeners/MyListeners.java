package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.Table.BaseTest;
import resources.ExtentReporter;

public class MyListeners extends BaseTest implements ITestListener {
	public static ExtentReports extent= ExtentReporter.getReportObject();
	public static ExtentTest test;
	       @Override
		  public void onTestStart(ITestResult result) {
			test= extent.createTest(result.getMethod().getMethodName());
			}
           @Override
		  public void onTestSuccess(ITestResult result) {
			  test.log(Status.PASS, "Test Passed");
		  }
          @Override
		  public void onTestFailure(ITestResult result) {
			  test.fail(result.getThrowable());
			  try {
				driver=(WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  
			  
			  
			  String filePath = null;
			try {
				Thread.sleep(1000);
				filePath = getScreenShot(result.getMethod().getMethodName(), driver);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test.addScreenCaptureFromPath(filePath,result.getMethod().getMethodName() );
			  
		  
		  }

		  
		  @Override
		  public void onTestSkipped(ITestResult result) {
		    
		  }

		 
		 @Override
		 public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		 
		  }

		 
		  @Override
		  public void onTestFailedWithTimeout(ITestResult result) {
		    onTestFailure(result);
		  }

		
		 @Override
		 public void onStart(ITestContext context) {
		  
		  }
		 
		  @Override
		  public void onFinish(ITestContext context) {
		
			  extent.flush();
		
			  }

	
}
