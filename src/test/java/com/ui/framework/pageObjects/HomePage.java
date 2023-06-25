package com.ui.framework.pageObjects;

import com.ui.framework.basePage.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public static final Logger logger = LogManager.getLogger(HomePage.class.getName());
    public HomePage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    /**
     * This Custom Method Clicks on Menu Item(`Appliances`) Based on provided text value.
     * @param matchingText
     * @throws Exception
     */
    public void clickOnMenuItemAppliances(String matchingText) throws Exception {
        logger.info("Click On Menu Item Appliances");
        clickWebElementBasedOnClassName("hmenu-item", matchingText);
    }

    /**
     * This Custom Method Clicks on Menu Item(`Television`) Based on provided text value.
     * @param matchingText
     * @return
     * @throws Exception
     */
    public TelevisionPage clickOnMenuItemTelevision(String matchingText) throws Exception {
        logger.info("Click On Menu Item Television");
        clickWebElementBasedOnClassName("hmenu-item", matchingText);
        return new TelevisionPage(driver);
    }
}
