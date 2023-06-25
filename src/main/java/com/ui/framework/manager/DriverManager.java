package com.ui.framework.manager;

import com.ui.framework.enums.DriverType;
import com.ui.framework.enums.EnvironmentType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class DriverManager {

    private WebDriver driver;

    private static DriverType driverType;

    private static EnvironmentType environmentType;

    public static final Logger logger = LogManager.getLogger(DriverManager.class.getName());

    /**
     * This Custom Method reads the value of `browser` & `environment` defined in the `config.properties` file.
     */
    public DriverManager() {
        logger.debug("Retrieve driver and environment types");
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    }

    /**
     * This Custom Method creates new driver.
     * @return - WebDriver
     */
    public WebDriver getDriver(){
        if(driver == null){
            logger.debug("Create Driver Object");
            driver = createDriver();
        }
        return driver;
    }

    /**
     * This Custom Method returns WebDriver based on matching `environment` retrieved from `config.properties` and
     * creates WebDriver Object accordingly based on reading the value.
     * @return
     */
    private WebDriver createDriver(){
        switch (environmentType) {
            case LOCAL:
                logger.info("Create Local Driver Object");
                driver = createLocal();
                break;
            case REMOTE:
                logger.info("Create Remote Driver Object");
                driver = createRemote();
                break;
        }
        return driver;
    }

    /**
     * This Custom Method provides Chrome Options required for setting Browser Capabilities.
     * @return - `options` object.
     */
    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setAcceptInsecureCerts(true);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        options.merge(capabilities);
        return options;
    }

    /**
     * This Custom Method creates a chromeDriver to execute tests on locally machine.
     * @return
     */
    private WebDriver createLocal(){
        switch (driverType){
            case CHROME:
                logger.info("Setup Chrome Driver");
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = this.getChromeOptions();
                driver = new ChromeDriver(options);
                break;
            default:
                break;
        }
        logger.info("Maximize Windows screen");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));
        logger.info("Creating Local Chrome Driver With Appropriate Chrome Options");
        return driver;
    }

    /**
     * This Custom Method Creates a ChromeDriver for executing tests in remote machine on Selenium Grid.
     * @return
     */
    private WebDriver createRemote(){
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--enable-javascript");
        options.addArguments("--disable-notifications");
        String hostName = "localhost";
        if (!System.getProperty("HUB_HOST").isEmpty())
            hostName = System.getProperty("HUB_HOST");
        logger.debug("HuB_HOST is set to : "+hostName);
        try {
            logger.debug("Attempt to Create WebDriver");
            driver = new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), options);
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            logger.info("Remote Driver Initialized:"+ hostName);
        } catch (MalformedURLException e) {
            logger.error("Malformed URL:"+hostName +" :"+e.getMessage());
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));
        logger.info("Creating Remote Chrome Driver With Appropriate Chrome Options");
        return driver;
    }

    /**
     * This Custom Method closes all browsers opened by WebDriver.
     */
    public void closeAllBrowsers(){
        logger.info("Close All Opened Tabs and Close Driver");
        for(String windowHandle : driver.getWindowHandles()){
            driver.switchTo().window(windowHandle);
            driver.close();
        }
    }
}
