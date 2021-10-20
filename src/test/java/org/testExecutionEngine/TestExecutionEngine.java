package org.testExecutionEngine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.applicationRelatedFunctionalities.pages.ApplicationActions;
import com.genericKeywords.GenericKeywords;
import com.utilities.ReporterUtilities;

public class TestExecutionEngine extends GenericKeywords {
	private static LinkedHashMap<String, LinkedList<String>> executionScenariosKeywordsList;
	private static HashMap<String, HashMap<String, String>> masterTestData;
	static ReporterUtilities reporterUtilObject = new ReporterUtilities();

	/*
	 * Objects for all Test actions class
	 */
	protected ApplicationActions applicationActions = new ApplicationActions();

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
