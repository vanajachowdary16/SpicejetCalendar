package testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utilities.BrowserUtility;
import pageobjects.SpicejetCalenderPageObjects2;

public class SpicejetCalendarTestOld {
	   private WebDriver driver;
	    private SpicejetCalenderPageObjects2 spicejetpageobjects;

	    public final static String spicejetUrl = "https://www.spicejet.com/";
    	BrowserUtility browserUtility = new BrowserUtility();

	    @BeforeClass
	    public void setUp() {

	    	browserUtility.launchBrowser();
			driver=browserUtility.getDriver();
			spicejetpageobjects= new SpicejetCalenderPageObjects2(driver);
			driver.get(spicejetUrl);	
			
	    }

	    @AfterSuite
	    public void tearDownBrowser() {
	    	browserUtility.tearDown();
	    }

	    @Test
	    public void testSpicejtDatePicker() throws InterruptedException {
	    	
	        spicejetpageobjects.enterFromToDetails("Chennai", "Delhi");
	        spicejetpageobjects.selectDate();
	        spicejetpageobjects.openPassengerPopupAndAddAdult();
	        spicejetpageobjects.clickSearch();
	        SpicejetCalenderPageObjects2.waitForpageToLoad(driver);
	        spicejetpageobjects.printFlights();
	    }
}
