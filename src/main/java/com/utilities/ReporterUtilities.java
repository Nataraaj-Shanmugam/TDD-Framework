package com.utilities;

import io.qameta.allure.Allure;

public class ReporterUtilities {

	public static void addDescription(String description){
		Allure.description(description);
	}

}
