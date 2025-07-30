package resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	
	public static ExtentReports getReportObject() {
	   // String path = System.getProperty("user.dir") + "\\reports\\index.html";
		String path=System.getProperty("user.dir")+"\\report\\index.html";
	//	new File(System.getProperty("user.dir")+"\\reports").mkdirs();
	    ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	    reporter.config().setReportName("Login Automation Test Report");
	    reporter.config().setDocumentTitle("Test Results");

	    ExtentReports extent = new ExtentReports();
	    extent.attachReporter(reporter);
	    extent.setSystemInfo("tester", "Manpreet");
	   

	    return extent;
	    
	}

}