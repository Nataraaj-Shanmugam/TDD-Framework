package com.apllicationRelatedFunctionalities.test;

import java.util.HashMap;
import java.util.List;

import org.testExecutionEngine.TestExecutionEngine;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utilities.ExcelUtilities;

public class TestClass1 extends TestExecutionEngine{
	
	@BeforeMethod
	public void bm(ITestResult result) {
		scenarioTestData.set((HashMap<String, String>) result.getParameters()[0]);
	}
	
	@Test(dataProvider = "dataprovider")
	public void testCase1(HashMap<String, String> map) {
		openBrowser(); 
		applicationActions.search();
		applicationActions.getProductNameAndValue();
	}
	
	@DataProvider(name = "dataprovider")
		public Object[][] dataprovider() {
		List<HashMap<String, String>> td = new ExcelUtilities(propFile.getProperty("TestDataWorkBook")).getTestDataFromDataSheet();
		Object[][] obj = new Object[td.size()][1];
		for(int i= 0; i < td.size();i++)
			obj[i][0] = td.get(i);
		return obj;
	}
		
	/*@Test(priority = 2)
	public void testCase2() {
		applicationActions.search();
		applicationActions.getProductNameAndValue();
	}
	
	
	
	@Test(priority = 3,testName = "Test for valiting secario 3")
	public void testCase3() {
		applicationActions.ClickOnTodaysDeal();
		applicationActions.search();
		applicationActions.getProductNameAndValue();
	}*/

}
