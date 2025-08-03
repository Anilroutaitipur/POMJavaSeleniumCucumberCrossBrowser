package stepdefinitions;

import basepackage.BaseClass;
import basepackage.Loginpage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import utility.*;

public class Hooks {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    public static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    Logger log = LoggerHelper.getLogger(Loginpage.class);

    @Before
    public void setup(Scenario scenario) {
        log.info("***** Starting Scenario: " + scenario.getName() + " *****");

        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        BaseClass.setDriver(browser);

        log.info("Launching browser: " + browser);

        ExtentTest test = extent.createTest(scenario.getName());
        scenarioTest.set(test);
    }


    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            log.warn("Scenario Failed: " + scenario.getName());
            scenarioTest.get().log(Status.FAIL, "Scenario failed");
            ScreenshotUtil.takeScreenshot(BaseClass.getDriver(), scenario.getName());
        } else {
            log.info("Scenario Passed: " + scenario.getName());
            scenarioTest.get().log(Status.PASS, "Scenario passed");
        }

        log.info("Closing browser and quitting WebDriver");
        extent.flush();
        //BaseClass.quitDriver();
    }
}
