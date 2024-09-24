package com.genericKeywords.Interface;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface UIGenericFunctionInterface {

    abstract void click(By locator);

    abstract void inputText(By locator, String value);

    abstract void inputTextWithActions(By locator, String value);

    abstract String getText(By locator);

    abstract String getText(WebElement element);

    abstract WebElement getElement(By locator);

    abstract List<WebElement> getElements(By locator);

    abstract void doubleClick(By locator);

    abstract void scrollToAnElement(By locator);

    abstract void dropDown(By locator, String selectionType, String option);

    abstract boolean isBlank(By locator);

    abstract boolean isBlank(String textValue);

    abstract void keyboardActions(By locator, String[] keyvalues);

    abstract String getRandomString(int stringLength);

    //Wait functionalities
    abstract WebElement waitUntilVisibiltyOfAnElement(int timeToWait, By locator);

    abstract WebElement waitUntilVisibiltyOfAnElement(int timeToWait, WebElement locator);

    abstract boolean waitUntilInVisibiltyOfAnElement(int timeToWait, By locator);

    abstract WebElement waitUntilClickableOfAnElement(int timeToWait, By locator);

    abstract WebElement waitUntilClickableOfAnElement(int timeToWait, WebElement locator);

    abstract WebElement waitUntilPresenceOfAnElement(int timeToWait, By locator);

    abstract boolean waitUntilAttributeContainsInElement(int timeToWait, By locator, String attribute, String value);

    abstract boolean waitUntilPresenceOfText(int timeToWait, By locator, String validationText);

    abstract boolean waitUntilPresenceOfText(int timeToWait, WebElement locator, String validationText);

    abstract void waitUntilFrameAvailability(int timeToWait, By locator);

    //Verifications
    abstract boolean verifyElementPresent(By locator);

    abstract boolean verifyElementNotPresent(By locator);

    abstract boolean verifyExactTextPresent(By locator, String validationtext);

    abstract boolean verifyPartialTextPresent(By locator, String validationtext);

    //Reporter functionalities

    abstract File takeScreenshotSpecificElement(By locator);

    abstract File takeScreenshotSpecificElement(WebElement element);


}
