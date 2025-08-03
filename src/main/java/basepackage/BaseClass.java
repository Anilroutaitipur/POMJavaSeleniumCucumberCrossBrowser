package basepackage;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utility.LoggerHelper;

public class BaseClass {

    private static final Logger log = LoggerHelper.getLogger(BaseClass.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(String browser)
    {
        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            driver.set(new EdgeDriver());
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            driver.set(new FirefoxDriver());
        }
        log.info("WebDriver instance set for thread: " + Thread.currentThread().getId());
    }

    public static WebDriver getDriver()
    {
        log.debug("Getting WebDriver instance");
        return driver.get();
    }

    public static void quitDriver()
    {
        if(driver.get()!=null)
        {
            log.info("Quitting WebDriver for thread: " + Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
            log.debug("WebDriver removed from ThreadLocal");
        }
        else {
            log.warn("Tried to quit WebDriver, but it was null for thread: " + Thread.currentThread().getId());
        }
    }
}
