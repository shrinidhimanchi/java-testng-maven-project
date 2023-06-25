package com.ui.framework.pageObjects;

import com.ui.framework.basePage.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class TelevisionPage extends BasePage {

    public static final Logger logger = LogManager.getLogger(TelevisionPage.class.getName());

    public TelevisionPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    /**
     * This Custom Method Scrolls To Particular Element based on it's matching section name.
     * @param matchingText
     * @throws Exception
     */
    public void moveToSectionOfType(String matchingText) throws Exception {
        logger.info("Scroll To Section : "+matchingText);
        waitForPageToLoad();
        scrollToWebElementBasedOnMatchingTextByClassName("a-section", matchingText);
    }

    /**
     * This Custom Method Clicks on a Child WebElement CheckBox Based On The Parent Matching Web Element's text.
     * @param matchingText
     * @throws Exception
     */
    public void clickOnCheckBoxOfType(String matchingText) throws Exception {
        logger.info("Click On CheckBox Based On Matching Text : "+matchingText);
        clickOnChildItemBasedOnMatchingText("a-list-item", matchingText, "a-checkbox");
    }


}
