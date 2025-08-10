package com.infosys.opencart.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviderUtility {

	//DataProvider 1
    @DataProvider(name = "LoginData")
    public static String[][] getLoginData() throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
        
        // ✅ Check if the Excel file exists before using it
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Excel file not found at path: " + path);
        }

        // Only create ExcelUtility after confirming file exists
        ExcelUtility excel = new ExcelUtility(path);
        
        String sheetName = "Sheet1"; // Make sure this matches your Excel file's sheet name

        int totalRows = excel.getRowCount(sheetName);     // excluding header row
        int totalCols = excel.getCellCount(sheetName, 0); // assuming row 0 is header

        String[][] loginData = new String[totalRows][totalCols];

        for (int i = 1; i <= totalRows; i++) { // Start from row 1 (skip header)
            for (int j = 0; j < totalCols; j++) {
                loginData[i - 1][j] = excel.getCellData(sheetName, i, j);
            }
        }

        return loginData; // returning 2d array
    }
  //DataProvider 2
    
  //DataProvider 3
    
  //DataProvider 4
    
}
