package com.genericKeywords;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();
    
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThread.set(driver);
    }

    public static void clearThread() {
        driverThread.remove();
    }
}
