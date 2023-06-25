package com.ui.framework.testCases;

import com.ui.framework.baseTest.BaseTest;
import com.ui.framework.pageObjects.HomePage;
import com.ui.framework.pageObjects.ResultPage;
import com.ui.framework.pageObjects.TelevisionPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Department extends BaseTest {

    SoftAssert softAssert = new SoftAssert();
    public static final Logger logger = LogManager.getLogger(Department.class.getName());


    /**
     * This Method opens `amazon` website and navigates through the menu-items to click on Second Highest Element
     * when selected from High to Low and returns the text for the specified web element.
     * @throws Exception
     */
    @Test
    public void validateAboutItemTextTest() throws Exception {
        HomePage homePage = new HomePage(driver);
        homePage.clickHamburgerMenu();
        homePage.clickOnMenuItemAppliances("TV, Appliances, Electronics");
        homePage.clickOnMenuItemTelevision("Televisions");
        TelevisionPage televisionPage = new TelevisionPage(driver);
        televisionPage.moveToSectionOfType("Brands");
        televisionPage.clickOnCheckBoxOfType("Samsung");
        ResultPage resultPage = new ResultPage(driver);
        resultPage.selectDropDownBasedOnText("Price: High to Low");
        softAssert.assertEquals(1, resultPage.getListOfWindowHandlers().size());
        String textReturned = resultPage.clickOnSecondItemFromResultsList(1);
        Reporter.log("The Value Of Text is : "+textReturned);
        softAssert.assertEquals(2, resultPage.getListOfWindowHandlers().size());
        Assert.assertTrue(resultPage.checkAboutItemExists(), "Element exists");
    }
}
