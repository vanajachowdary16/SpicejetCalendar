package testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utilities.BaseTest;
import pageobjects.SpicejetCalenderPageObjects;

public class SpicejetCalendarTest {

    private WebDriver driver;
    private SpicejetCalenderPageObjects spicejetpageobjects;

    public final static String spicejetUrl = "https://www.spicejet.com/";

    @BeforeClass
    public void setUp() {

    	BaseTest.launchBrowser();
		driver=BaseTest.getDriver();
		spicejetpageobjects= new SpicejetCalenderPageObjects(driver);
		driver.get(spicejetUrl);	
		
    }

    @AfterSuite
    public void tearDownBrowser() {
        BaseTest.tearDown();
    }

    @Test
    public void testSpicejtDatePicker() throws InterruptedException {
    	
        spicejetpageobjects.enterFromToDetails("Chennai", "Delhi");
        spicejetpageobjects.selectDate();
        spicejetpageobjects.openPassengerPopupAndAddAdult();
        spicejetpageobjects.clickSearch();
        SpicejetCalenderPageObjects.waitForpageToLoad(driver);
        spicejetpageobjects.printFlights();
    }
}
