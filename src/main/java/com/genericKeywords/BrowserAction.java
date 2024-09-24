package com.genericKeywords;

import com.genericKeywords.Interface.BrowserActionInterface;
import io.qameta.allure.Allure;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import static testExecutionEngine.TestExecutionEngine.propFile;

public class BrowserAction implements BrowserActionInterface {
    NonUIGenericFunction nonUIGenericFunction = new NonUIGenericFunction();

    @Override
    public void launchBrowser() {
        //todo get from properties
        String browserName = System.getProperty("Browser");
        try {
            DriverFactory.setDriver(switch (browserName) {
                case "Chrome" -> new ChromeDriver();
                case "Firefox" -> new FirefoxDriver();
                case "Edge" -> new EdgeDriver();
                case "Safari" -> new SafariDriver();
                default -> throw new IllegalStateException("Unexpected value: " + browserName);
            });
            Allure.step("Created " + browserName + " browser instance");
        } catch (Exception e) {
            throw new RuntimeException("Unable to recoganize " + browserName + " browser details", e);
        }
        loadURL(propFile.getProperty("URL"));
    }


    @Override
    public void closeBrowser() {
        DriverFactory.getDriver().quit();
    }

    @Override
    public void loadURL(String URL) {
        try {
            DriverFactory.getDriver().navigate().to(URL);
            Allure.step("Loaded URL : " + URL);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load URL '" + URL + "'", e);
        }
    }

    @Override
    public void navigateBack() {
        try {
            DriverFactory.getDriver().navigate().back();
            Allure.step("Navigated to previous page");
        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate back to previous page", e);
        }
    }

    @Override
    public void navigateForward() {
        try {
            DriverFactory.getDriver().navigate().forward();
            Allure.step("Navigated to next page");
        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate to next page", e);
        }
    }

    @Override
    public void refreshPage() {
        DriverFactory.getDriver().navigate().refresh();
        Allure.step("Refreshed webpage");
    }


    @Override
    public String getUrl() {
        return DriverFactory.getDriver().getCurrentUrl();
    }

}
