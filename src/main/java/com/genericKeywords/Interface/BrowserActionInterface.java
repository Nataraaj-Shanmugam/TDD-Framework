package com.genericKeywords.Interface;

public interface BrowserActionInterface {

    void launchBrowser();

    void closeBrowser();

    void loadURL(String URL);

    void navigateBack();

    void navigateForward();

    void refreshPage();

    String getUrl();

}
