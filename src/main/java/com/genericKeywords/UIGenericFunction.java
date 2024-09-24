package com.genericKeywords;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;

import com.genericKeywords.Interface.UIGenericFunctionInterface;
import com.utilities.PropertyFileUtility;
import io.qameta.allure.Allure;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static testExecutionEngine.TestExecutionEngine.propFile;

public class UIGenericFunction implements UIGenericFunctionInterface {
    private static String propertyFilePath = "src/test/resources/ApplicationAndExecution.properties";
    public static String reportPath;

    static {
        try {
            propFile = new PropertyFileUtility(propertyFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reportPath = System.getProperty("user.dir") + "\\" + propFile.getProperty("ReportsLocation") + "\\" + "Test Execution Report_" + Calendar.getInstance().getTime().toString().replace(" ", "_").replace(":", "_");
    }

    @Override
    public void click(By locator) {
        try {
            getElement(locator).click();
            Allure.step("Click on element");
        } catch (Exception e) {
            try {
                ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", locator);
                Allure.step("Click on element");
            } catch (Exception e1) {
                try {
                    new Actions(DriverFactory.getDriver()).moveToElement(getElement(locator)).click(getElement(locator)).perform();
                    Allure.step("Click on element");
                } catch (Exception e2) {
                    throw new RuntimeException("Unable to click on element " + locator, e2);
                }
            }
        }
    }

    @Override
    public void inputText(By locator, String value) {
        try {
            getElement(locator).sendKeys(value);
            Allure.step("Entered text : '" + value + "'");
        } catch (Exception e) {
            throw new RuntimeException("Unable to enter '" + value + "' value to element " + locator, e);
        }
    }

    @Override
    public void inputTextWithActions(By locator, String value) {
        try {
            new Actions(DriverFactory.getDriver()).moveToElement(getElement(locator)).sendKeys(getElement(locator), Keys.CLEAR, value).perform();
            Allure.step("Entered text :" + value);
        } catch (Exception e) {
            throw new RuntimeException("Unable to enter '" + value + "' value to element " + locator, e);
        }
    }


    @Override
    public String getText(By locator) {
        return getText(getElement(locator));
    }

    @Override
    public String getText(WebElement element) {
        waitUntilVisibiltyOfAnElement(NumberUtils.toInt(propFile.getProperty("DefaultWaitTime")), element);
        return element.getText();
    }


    @Override
    public WebElement getElement(By locator) {
        return waitUntilVisibiltyOfAnElement(NumberUtils.toInt(propFile.getProperty("DefaultWaitTime")), locator);
    }

    @Override
    public List<WebElement> getElements(By locator) {
        return DriverFactory.getDriver().findElements(locator);
    }

    @Override
    public void doubleClick(By locator) {
        try {
            new Actions(DriverFactory.getDriver()).moveToElement(getElement(locator)).doubleClick(getElement(locator)).perform();
            Allure.step("Double clicked on element: " + locator);
        } catch (Exception e) {
            throw new RuntimeException("Unable to perform Actions double click for an element :" + locator, e);
        }
    }

    @Override
    public void scrollToAnElement(By locator) {
        try {
            ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", locator);
            Allure.step("Scrolled to element :" + locator);
        } catch (Exception e) {
            throw new RuntimeException("Unable to perform Actions Scroll to an element :" + locator, e);
        }
    }

    @Override
    public File takeScreenshotSpecificElement(By locator) {
        return takeScreenshotSpecificElement(getElement(locator));
    }

    @Override
    public File takeScreenshotSpecificElement(WebElement element) {
        return element.getScreenshotAs(OutputType.FILE);
    }

    //    todo @Override
    public File takeScreenshot() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
    }

    @Override
    public void dropDown(By locator, String selectionType, String option) {
        boolean flag = false;
        try {
            if (locator.toString().contains("select")) {
                Select dropdown = new Select(getElement(locator));
                switch (selectionType.toLowerCase()) {
                    case "index":
                        dropdown.selectByIndex(NumberUtils.toInt(option));
                        flag = true;
                        break;
                    case "value":
                        dropdown.selectByValue(option);
                        flag = true;
                        break;
                    case "text":
                        dropdown.selectByVisibleText(option);
                        flag = true;
                        break;
                    default:
                        break;
                }
            } else {
                for (WebElement eachElement : getElements(locator)) {
                    if (eachElement.getAttribute(selectionType).equals(option)) {
                        eachElement.click();
                        flag = true;
                        break;
                    }
                }
            }
            if (flag)
                Allure.step("Selected " + option + " " + selectionType + " in the dropdown element :" + locator);
            else
                throw new Exception("Unable to choose any element in the dropdown");
        } catch (Exception e) {
            throw new RuntimeException("Unable to find the " + option + " " + selectionType + " in the dropdown element :" + locator, e);
        }
    }

    @Override
    public boolean isBlank(By locator) {
        return isBlank(getText(locator));
    }

    @Override
    public boolean isBlank(String stringValue) {
        if (stringValue.trim().isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public void keyboardActions(By locator, String[] keyvalues) {
        new Actions(DriverFactory.getDriver()).sendKeys(getElement(locator), Keys.CLEAR, Keys.chord(keyvalues)).perform();
    }

    @Override
    public String getRandomString(int stringLength) {
        return RandomStringUtils.randomAlphabetic(stringLength);
    }

    @Override
    public WebElement waitUntilVisibiltyOfAnElement(int timeToWait, By locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Override
    public WebElement waitUntilVisibiltyOfAnElement(int timeToWait, WebElement locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.visibilityOf(locator));
    }

    @Override
    public boolean waitUntilInVisibiltyOfAnElement(int timeToWait, By locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @Override
    public WebElement waitUntilClickableOfAnElement(int timeToWait, By locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Override
    public WebElement waitUntilClickableOfAnElement(int timeToWait, WebElement locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Override
    public WebElement waitUntilPresenceOfAnElement(int timeToWait, By locator) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Override
    public boolean waitUntilAttributeContainsInElement(int timeToWait, By locator, String attribute, String validationText) {
        waitUntilPresenceOfAnElement(timeToWait, locator);
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.attributeContains(locator, attribute, validationText));
    }

    @Override
    public boolean waitUntilPresenceOfText(int timeToWait, By locator, String validationText) {
        waitUntilPresenceOfAnElement(timeToWait, locator);
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.textToBePresentInElementLocated(locator, validationText));
    }

    @Override
    public boolean waitUntilPresenceOfText(int timeToWait, WebElement locator, String validationText) {
        waitUntilVisibiltyOfAnElement(timeToWait, locator);
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.textToBePresentInElement(locator, validationText));
    }

    @Override
    public void waitUntilFrameAvailability(int timeToWait, By locator) {
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeToWait)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    @Override
    public boolean verifyElementPresent(By locator) {
        if (getElements(locator).size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean verifyElementNotPresent(By locator) {
        return (!verifyElementPresent(locator));
    }

    @Override
    public boolean verifyExactTextPresent(By locator, String validationtext) {
        boolean flag = false;
        if (getText(locator).equals(validationtext))
            flag = true;
        return flag;
    }

    @Override
    public boolean verifyPartialTextPresent(By locator, String validationtext) {
        boolean flag = false;
        if (getText(locator).contains(validationtext))
            flag = true;
        return flag;
    }
}
