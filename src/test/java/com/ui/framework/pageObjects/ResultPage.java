package com.ui.framework.pageObjects;

import com.ui.framework.basePage.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPage extends BasePage {

    public static final Logger logger = LogManager.getLogger(ResultPage.class.getName());
    public ResultPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    /**
     * This Custom Method Selects value from dropdown based on the option value.
     */
    public void selectDropDownBasedOnValue(){
        logger.info("Click On Drop Down Based On Value");
        waitForPageToLoad();
        selectDropDownBasedOnValue("s-result-sort-select");
    }

    /**
     * This Custom Method Selects value from dropdown based on the option value.
     */
    public void selectDropDownBasedOnText(String input){
        logger.info("Click On Drop Down Based On Value");
        waitForPageToLoad();
        selectDropDownBasedOnText("s-result-sort-select", input);
    }

    /**
     * This Custom Method Clicks On SecondItem from the displayed WebElements Lists and returns the text of a particular WebElement.
     * @param itemNumberToBeClicked
     * @throws Exception
     */
    public String clickOnSecondItemFromResultsList(Integer itemNumberToBeClicked) throws Exception {
        logger.info("Click On Second Item From Results List");
        waitForPageToLoad();
        clickItemNumberAndSwitchToNewTab("div[data-component-type='s-search-result']", itemNumberToBeClicked);
        scrollToWebElementBasedOnId("feature-bullets");
        String text = getTextForAboutThisItem("feature-bullets");
        System.out.println("Returned Text is : "+text);
        return text;
    }

    /**
     * This Custom Method checks if the WebElement is displayed on the screen.
     * @return
     */
    public Boolean checkAboutItemExists(){
        logger.info("Check AboutItem Element exists");
        return isElementDisplayed(By.id("feature-bullets"));
    }
}
