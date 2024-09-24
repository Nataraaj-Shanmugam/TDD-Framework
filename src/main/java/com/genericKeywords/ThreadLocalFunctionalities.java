package com.genericKeywords;

import java.util.HashMap;
import java.util.LinkedList;

public class ThreadLocalFunctionalities {

    //Specific scenario TestData details
    protected static ThreadLocal<HashMap<String, String>> scenarioTestData = new ThreadLocal<HashMap<String, String>>();

    public static HashMap<String, String> getTestData() {
        return scenarioTestData.get();
    }
}
