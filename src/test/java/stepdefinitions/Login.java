package stepdefinitions;

import basepackage.BaseClass;
import basepackage.Homepage;
import basepackage.Loginpage;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utility.ConfigPropertiesReader;
import utility.ExcelReader;
import utility.LoggerHelper;
import utility.WaitClass;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class Login {

    WebDriver driver;
    Loginpage loginpage;
    Homepage homepage;
    WaitClass wait;

    String url = ConfigPropertiesReader.getProperty("envurl");
    String username = ConfigPropertiesReader.getProperty("username");
    String password = ConfigPropertiesReader.getProperty("password");

    Logger log = LoggerHelper.getLogger(Login.class);

    @Given("User navigate to login page")
    public void user_navigate_to_login_page() {
        log.info("Navigating to login page: " + url);
        driver = BaseClass.getDriver(); // Get from Hooks
        driver.manage().window().maximize();
        driver.get(url);

        // Initialize Page Objects
        loginpage = new Loginpage(driver);
        homepage = new Homepage(driver);
        wait = new WaitClass(driver);
    }

    @When("User enter username and password")
    public void user_enter_username_password() {
        log.info("Entering username and password from config");
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
    }

    @When("User enter the {string} and {string}")
    public void user_enter_credentials(String username, String password) {
        log.info("Entering credentials from scenario: Username = " + username);
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
    }

    @And("User click on login button")
    public void user_click_on_login_button() {
        log.info("Clicking on Login button");
        loginpage.clickLogin();
    }

    @Then("User should navigate to Home page")
    public void user_should_navigate_to_home_page() {
        wait.implicitWait();
        assertTrue(homepage.verifyShoppingCartLink());
    }

    @Then("User login will fail takes screenshot")
    public void user_should_not_navigate_to_home_page() {
        log.warn("Verifying failed login attempt");
        wait.implicitWait();
        assertTrue(!homepage.verifyShoppingCartLink());
        log.warn("Login failed - Home page not loaded");
    }

    @Given("User reads login data from Excel and logs in")
    public void user_reads_from_excel_and_logs_in() throws Exception {
        String path = "testdata/LoginData.xlsx";
        log.info("Reading login data from Excel file: " + path);
        List<String[]> loginData = ExcelReader.getLoginData(path, "Sheet1");

        for (String[] credentials : loginData) {
            String username = credentials[0];
            String password = credentials[1];

            log.info("Navigating to the login page: " + url);
            driver = BaseClass.getDriver(); // Get from Hooks
            driver.manage().window().maximize();
            driver.get(url);

            // Initialize Page Objects
            loginpage = new Loginpage(driver);
            homepage = new Homepage(driver);
            wait = new WaitClass(driver);

           // Login
            Loginpage loginpage = new Loginpage(driver);
            loginpage.enterUsername(username);
            loginpage.enterPassword(password);
            loginpage.clickLogin();

            // Assert home page
            homepage = new Homepage(driver);
            boolean isLoginSuccess = homepage.verifyShoppingCartLink();
            assertTrue(homepage.verifyShoppingCartLink());

            log.info("Login success: " + isLoginSuccess);

            //driver.quit(); // close for each iteration


        }
    }
}