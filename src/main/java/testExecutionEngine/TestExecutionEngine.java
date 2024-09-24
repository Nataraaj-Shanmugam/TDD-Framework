package testExecutionEngine;

import java.util.*;

import com.genericKeywords.UIGenericFunction;
import com.utilities.PropertyFileUtility;

public class TestExecutionEngine extends UIGenericFunction {
    private static LinkedHashMap<String, LinkedList<String>> executionScenariosKeywordsList;
    private static HashMap<String, HashMap<String, String>> masterTestData;
    public static PropertyFileUtility propFile;
    public ThreadLocal<HashMap<String, Object>> testCaseTempData = new ThreadLocal<>();

    /**
     * Function to create Extent report and instantiate reporter object
     *
     * @author Nataraaj
     */
	/*@BeforeClass
	public void beforeclass() {
		reporterUtilObject.createReporterFile();
	}
	
	*//**
     * Function to Initiate resources - Driver, Reporter
     *
     * @author Nataraaj
     *//*
			@BeforeMethod()
			public void scenarioKeyword() {
			//		String scenarioName =  (String) result.getParameters()[0];
			String scenarioName = "testing";
			reporterUtilObject.createReporterTest(scenarioName);
			//		scenarioKeywordList.set(executionScenariosKeywordsList.get(scenarioName));
			//		scenarioTestData.set(masterTestData.get(scenarioName));
			}*/

    /**
     * Function to close resources - Driver
     *
     * @author Nataraaj
     */
	/*@AfterMethod
	public void afterclass() {
		try {
			reporterUtilObject.writeDataToReport();
			closeBrowser();
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e, "Exception while closing browser");
		}
	}*/


}
