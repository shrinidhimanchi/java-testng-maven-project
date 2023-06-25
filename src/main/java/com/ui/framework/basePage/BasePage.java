package com.ui.framework.basePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public abstract class BasePage {
    public static final Logger logger = LogManager.getLogger(BasePage.class.getName());
    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    /**
     * method `clickHamburgerMenu` clicks on HamBurger Menu Item
     */
    public void clickHamburgerMenu(){
        logger.info("Click On Hamburger Menu");
        this.clickOnWebElement(this.findEle(By.id("nav-hamburger-menu")));
    }

    /**
     * This Method waits for the element to be visible until it is found.
     * @param element - element required for performing explicit wait until it is available
     * @param timeOutInSeconds - Duration to wait.(defined in `seconds`) until it is visible.
     * @return Returns WebElement for carrying our further tasks on WebElement.
     */
    public WebElement waitForElementVisibility(WebElement element, long timeOutInSeconds){
        logger.debug("Wait For Element Visibility : "+element.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    /**
     * This Method retrieves the Child WebElement based on the matching parent until it is visible on screen.
     * @param element - parent WebElement
     * @param className - className to identify the parent WebElement
     * @param timeOutInSeconds - Duration to wait.(defined in `seconds`) until it is visible.
     * @return Returns WebElement for carrying our further tasks on WebElement.
     */
    public WebElement getNestedWebElementBasedOnMatchingParentClassName(WebElement element, String className, long timeOutInSeconds){
        logger.debug("Get Nested WebElement : "+element.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        WebElement nestedElement = element.findElement(By.className((className)));
        wait.until(ExpectedConditions.elementToBeClickable(nestedElement));
        return nestedElement;
    }

    /**
     * This Method waits for page to Load until the javascript returns the `readyState` to `complete`.
     */
    public void waitForPageToLoad(){
        logger.info("Wait For Page To Load");
        ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().
                        equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(expectedCondition);
    }

    /**
     * This Custom Method to Click on Web Element until it is visible on the screen.
     * @param element
     */
    public void clickOnWebElement(WebElement element){
        logger.debug("Click On WebElement : "+element.toString());
        this.waitForElementVisibility(element, 5);
        element.click();
    }

    /**
     * This Method returns Web Element Based on specific `By` locators
     * @param by - Locator used to locate the WebElement uniquely.
     * @return - Returns WebElement for carrying our further tasks on WebElement.
     */
    public WebElement findEle(By by){
        logger.debug("Get WebElement : "+by.toString());
        return driver.findElement(by);
    }

    /**
     * This Method returns the List Of WebElement Based On the Matching Locator
     * @param by Locator to retrieve list of WebElements
     * @return - Returns List Of WebElement for carrying our further tasks on WebElement.
     */
    public List<WebElement> findEles(By by){
        logger.debug("Get List Of WebElement : "+by.toString());
        return driver.findElements(by);
    }

    /**
     * This Custom Method returns Web Element Based on Locator & Matching Text
     * @param by Specific Locator Used to locate the WebElement On Screen.
     * @param input Matching Text to return the element based on matching text.
     * @return - Returns WebElement for carrying our further tasks on WebElement.
     * @throws Exception - Throws Exception if no Matching Text is found from the list of WebElements.
     */
    public WebElement getElementBasedOnMatchingText(By by, String input) throws Exception {
        logger.debug("Get Element Based On Matching Text : "+input);
        for(WebElement element : this.findEles(by)){
            //System.out.println("Text Returned is : "+element.getText());
            if(element.getText().equals(input)){
                return element;
            }
        };
        throw new Exception("No Matching Element found");
    }

    /**
     * This Custom Method returns list of Web Elements Based on Css Selector
     * @param cssSelector - Locator used to retrieve list of Web Elements Based On `cssSelector` locator.
     * @return - Returns List Of WebElement for carrying our further tasks on WebElement.
     * @throws Exception throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public List<WebElement> getListOfWebElementsBasedOnCssSelector(String cssSelector) throws Exception {
        logger.debug("Get List Of Web Element Based On CSS Selector");
        List<WebElement> elements = this.findEles(By.cssSelector(cssSelector));
        if(elements.size() > 0){
            return elements;
        } else {
            throw new Exception("No Matching Criteria Found");
        }
    }

    /**
     * This Custom Method Clicks on Web Element Based on `className` locator & Matching Text
     * @param className - `className` locator to uniquely identify the Web Element on Screen.
     * @param matchingText - Click on specified Web Element Based on matching text.
     * @throws Exception throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public void clickWebElementBasedOnClassName(String className, String matchingText) throws Exception {
        logger.debug("Click On Web Element Based On Class Name & Matching Text : "+matchingText);
        clickOnWebElement(getElementBasedOnMatchingText(By.className(className), matchingText));
    }

    /**
     * This Custom Method Scrolls On Particular Web Element Based On `ClassName` locator & `matchingText`
     * @param className - `className` locator to uniquely identify the Web Element on Screen.
     * @param matchingText - Scroll to specified Web Element Based on matching text.
     * @throws Exception throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public void scrollToWebElementBasedOnMatchingTextByClassName(String className, String matchingText) throws Exception {
        logger.debug("Scroll To Web Element Based On Matching Text By Class Name & Matching Text");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElementBasedOnMatchingText(By.className(className), matchingText);
        jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * This Custom Method Scrolls To Particular WebElement Based On `id` locator
     * @param id - Locator to uniquely identify the Web Element on Screen.
     */
    public void scrollToWebElementBasedOnId(String id) {
        logger.debug("Scroll To Web Element Based On Id : "+id);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = this.findEle(By.id(id));
        jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * This Custom Method Clicks on child WebElement Based On Matching Text
     * @param parentClassName - `className` locator to retrieve the list of web elements.
     * @param matchingText - `matchingText` to find a match of Child WebElement.
     * @param childClassName - `className` locator to retrieve the web element.
     * @throws Exception - throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public void clickOnChildItemBasedOnMatchingText(String parentClassName, String matchingText, String childClassName ) throws Exception {
        logger.debug("Click On Child Item Based On Matching Text");
        WebElement element = this.getElementBasedOnMatchingText(By.className(parentClassName), matchingText);
        clickOnWebElement(this.getNestedWebElementBasedOnMatchingParentClassName(element, childClassName, 10));
    }

    /**
     * This Custom Method selects a value from dropdown based on Visible Text
     * @param className - `className` locator to identify WebElement/dropdown on Screen.
     * @param matchingText - `matchingText` criteria to select dropdown based on Visible Text.
     */
    public void selectDropDownBasedOnText(String className, String matchingText){
        logger.info("Select Drop Down Based On Visible Text");
        Select select = new Select(this.waitForElementVisibility(this.findEle(By.id(className)), 15));
        select.selectByVisibleText(matchingText);
    }

    /**
     * This Custom Method selects a value from dropdown based on option value.
     * @param className `className` locator to identify WebElement/dropdown on Screen.
     */
    public void selectDropDownBasedOnValue(String className){
        logger.debug("Select Drop Down Based On Value");
        Select select = new Select(this.waitForElementVisibility(this.findEle(By.id(className)), 15));
        select.selectByValue("price-desc-rank");
    }

    /**
     * This Custom Method Clicks on Second Highest Priced Item Based on Index Value
     * @param cssSelector - `cssSelector` locator to identify list of WebElements on the Screen.
     * @param elementToBeClicked - To Click On Second Item retrieved from the List.
     * @return
     * @throws Exception - throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public String clickOnSecondHighestPricedItem(String cssSelector, Integer elementToBeClicked) throws Exception {
        logger.info("Click On Second Highest Priced Item");
        List<WebElement> elements = this.getListOfWebElementsBasedOnCssSelector(cssSelector);
        String parentWindowHandler = driver.getWindowHandle();
        clickOnWebElement(elements.get(elementToBeClicked));
        return parentWindowHandler;
    }

    /**
     * This Custom Method Clicks on Second WebElements retrieved from the List of Web Elements and switches the driver to
     * new Window Handler and returns the same.
     * @param cssSelector - `cssSelector` to uniquely identify WebElements On The Screen.
     * @param elementToBeClicked - To Click On Second Item retrieved from the List.
     * @return
     * @throws Exception - throws an Exception if no Matching Element is found from the retrieved list of Web Elements.
     */
    public String clickItemNumberAndSwitchToNewTab(String cssSelector, Integer elementToBeClicked) throws Exception {
        logger.debug("Click On Item Number And Switch To New Tab");
        String parentWindowHandler = this.clickOnSecondHighestPricedItem(cssSelector, elementToBeClicked);
        for(String windowHandle : this.getListOfWindowHandlers()){
            if(!windowHandle.equals(parentWindowHandler)){
                driver.switchTo().window(windowHandle);
                return parentWindowHandler;
            }
        }
        throw new Exception("Someone closed the browser accidentally & hence couldn't find any window handlers");
    }

    /**
     * This Custom Method Returns the text for the particular WebElement by using the property `getText`
     * @param id - To Uniquely Identify the WebElement On the Screen.
     * @return - returns the text from WebElement by using `getText` property
     */
    public String getTextForAboutThisItem(String id){
        logger.info("Get Text For About This Item");
        return getTextFromWebElement(this.findEle(By.id(id)));
    }

    /**
     * This Custom Method returns text for a given WebElement by using WebDriverWait until the WebElement is visible.
     * @param element - To Uniquely Identify the WebElement On the Screen.
     * @return - returns the text from WebElement by using `getText` property
     */
    public String getTextFromWebElement(WebElement element){
        logger.debug("Get Text For About This Item");
        return this.waitForElementVisibility(element, 10).getText();
    }

    /**
     * This Custom Method returns the Set Of WindowHandlers(i.e, opened by WebDriver)
     * @return
     */
    public Set<String> getListOfWindowHandlers(){
        logger.debug("Get List Of Window Handlers");
        return driver.getWindowHandles();
    }

    /**
     * This Custom Method checks if the Web Element is displayed on screen.
     * @param by  - Locator to uniquely identify Web Element On The Screen.
     * @return Boolean Value `true` if WebElement is Displayed, else returns `false`.
     */
    public Boolean isElementDisplayed(By by){
        logger.debug("Check If Element is Displayed");
        return this.waitForElementVisibility(this.findEle(by), 10).isDisplayed();
    }

}
