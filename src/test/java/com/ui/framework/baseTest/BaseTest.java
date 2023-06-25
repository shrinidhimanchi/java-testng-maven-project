package com.ui.framework.baseTest;

import com.ui.framework.manager.DriverManager;
import com.ui.framework.manager.FileReaderManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;


public abstract class BaseTest {

    protected WebDriver driver;

    public static final Logger logger = LogManager.getLogger(BaseTest.class.getName());

    DriverManager driverManager;

    /**
     * This Method gets executed for every test-case to set the configuration & browser in a certain state to run the Selenium tests.
     */
    @BeforeMethod
    public void initialise(){
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        String log4j2ConfigurationPath = absolutePath + "/log4j2.properties";
        Configurator.initialize(null, log4j2ConfigurationPath);
        driverManager = new DriverManager();
        logger.info("Browser Is Opened");
        driver = driverManager.getDriver();
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
    }

    /**
     * This Method gets executed to perform gracious shutdown of browser and close the driver session.
     */
    @AfterMethod
    public void tearDown() {
        driverManager.closeAllBrowsers();
    }
}
