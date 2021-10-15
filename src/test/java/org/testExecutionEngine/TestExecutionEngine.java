package org.testExecutionEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.genericKeywords.GenericKeywords;
import com.genericKeywords.ProjectCustomException;
import com.utilities.ExcelUtilities;
import com.utilities.ReporterUtilities;

public class TestExecutionEngine extends GenericKeywords{
	private static LinkedHashMap<String, LinkedList<String>> executionScenariosKeywordsList;
	private static HashMap<String, HashMap<String, String>> masterTestData;
	static ReporterUtilities reporterUtilObject = new ReporterUtilities();
	public static boolean rerunExecution = false;

	/**
	 * Function to create Extent report and instantiate reporter object
	 * @author Nataraaj
	 */
	@BeforeClass
	@Parameters({"ExecutionType"})
	public void beforeclass(@Optional("") String executionType) {
		if(executionType.equals("Rerun"))
			rerunExecution = true;
		reporterUtilObject.createReporterFile(rerunExecution);
	}

	/**
	 * Function to Initiate resources - Driver, Reporter
	 * @author Nataraaj
	 */
	@BeforeMethod()
	public void scenarioKeyword(ITestResult result) {
		String scenarioName =  (String) result.getParameters()[0];
		reporterUtilObject.createReporterTest(scenarioName);
		scenarioKeywordList.set(executionScenariosKeywordsList.get(scenarioName));
		scenarioTestData.set(masterTestData.get(scenarioName));
		openBrowser(); 
	}


	/**
	 * Execution engine which take cares of the 
	 * @author Nataraaj
	 */
	@Test(dataProvider = "dataProvider")
	public void executionEngine(String scenario) {
		Class<?> classFileName;
		try {
			classFileName = Class.forName("com.applicationRelatedFunctionalities.ApplicationActions");
			for (String eachKeyword : getScenarioKeyword()) {
				boolean flag= false;
				try {
					Method[] methodObject = classFileName.getDeclaredMethods();
					for (Method method : methodObject) {
						if(method.getName().equalsIgnoreCase(eachKeyword) && method.getParameters().length == 0) {
							method.invoke(classFileName.getDeclaredConstructor().newInstance(), (Object[])method.getParameters());
							flag=true;
						}
						if(flag)
							break;
					}
					if(!flag)
						throw new NoSuchMethodException();
				} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
					System.exit(0);
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"");
		}
	}

	/**
	 * Function to close resources - Driver
	 * @author Nataraaj
	 */
	@AfterMethod
	public void afterclass() {
		try {
			reporterUtilObject.writeDataToReport();
			closeBrowser();
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Exception while closing browser");
		}
	}

	/**
	 * Function to get Testdata and Keywords for all executing scenarios
	 * @return
	 * @author Nataraaj
	 */
	@DataProvider(name = "dataProvider", parallel = true)
	public Iterator<String> dataProvider() {
			ExcelUtilities excelObject = new ExcelUtilities(propFile.getProperty("TestDataWorkBook"));
			executionScenariosKeywordsList = excelObject.masterKeywordDetails();
			masterTestData = excelObject.masterTestDataSet();
			return masterTestData.keySet().iterator();
	}
}
