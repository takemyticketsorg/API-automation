package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportmanager implements ITestListener{

public ExtentSparkReporter sparkReporter;
public ExtentReports extentReports;
public ExtentTest test;

String repName;

public void onStart(ITestContext testContext) {
String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
repName ="Test-Report-" + timeStamp + ".html";
sparkReporter= new ExtentSparkReporter(".\\reports\\" + repName);

sparkReporter.config().setDocumentTitle("API Automation Report");
sparkReporter.config().setReportName("API Test Report");
sparkReporter.config().setTheme(Theme.STANDARD);

extentReports = new ExtentReports();
extentReports.attachReporter(sparkReporter);
extentReports.setSystemInfo("Application", "TakeMyTickets");
extentReports.setSystemInfo("UserName", System.getProperty("user.name"));
extentReports.setSystemInfo("Environment", "QA");

String os = testContext.getCurrentXmlTest().getParameter("os");
extentReports.setSystemInfo("Operating System", os);

List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
if(!includedGroups.isEmpty()) {
extentReports.setSystemInfo("Groups", includedGroups.toString());
}
}

public void onTestSuccess(ITestResult result) {
test = extentReports.createTest(result.getTestClass().getName());
test.assignCategory(result.getMethod().getGroups());
test.log(Status.PASS, result.getName()+" got successfully executed");
}
public void onTestFailure(ITestResult result) {
test = extentReports.createTest(result.getTestClass().getName());
test.assignCategory(result.getMethod().getGroups());
test.log(Status.FAIL, result.getName()+" got failed");  
test.log(Status.INFO,result.getThrowable().getMessage());
}
public void onTestSkipped(ITestResult result) {
test = extentReports.createTest(result.getTestClass().getName());
test.assignCategory(result.getMethod().getGroups());
test.log(Status.SKIP, result.getName()+" got skipped");  
test.log(Status.INFO,result.getThrowable().getMessage());
}

public void onFinish(ITestContext context) {
extentReports.flush();
String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
File report = new File(pathOfExtentReport);

try {
Desktop.getDesktop().browse(report.toURI());
}
catch(Exception e) {
e.printStackTrace();

}


}



}