package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.BrowserUtility;

public class SpicejetCalenderPageObjects2 extends BrowserUtility {

	// Only By locators at class level — no WebElement or wait initialization here

	public static final String depature_year = "2026";
	public static final String depature_month = "January";
	public static final String depature_day = "12";

	public static final String return_year = "2026";
	public static final String return_month = "November";
	public static final String return_day = "18";
	public static final String adultCount = "1";
	public static final String fromAirportName = "Chennai International Airport";
	public static final String toAirportName = "DEL - Delhi, India";

	//locator to select date 
	public static final By depatureMonthLocator = By.xpath("//div[@data-testid='undefined-month-"+depature_month+"-"+depature_year+"']");
	public static final By returnMonthLocator = By.xpath("//div[@data-testid='undefined-month-"+return_month+"-"+return_year+"']");
	public static final By depatureDay = By.xpath(".//div[@data-testid='undefined-calendar-day-"+depature_day+"']");
	public static final By returnDay = By.xpath(".//div[@data-testid='undefined-calendar-day-"+return_day+"']");
	
	public static final By calenderIconLocator = By
			.xpath("//div[contains(text(), 'Departure Date')]//following-sibling::div");
	public static final By calenderIconLocator2 = By
			.xpath("//div[contains(text(), 'Return Date')]//following-sibling::div");
	public static final By clickFromInputLocator = By.xpath("//div[text()='From']");
	public static final By clickToInputLocator = By.xpath("//div[text()='To']");

	public static final By fromInputLocator = By.xpath("//div[@data-testid='to-testID-origin']//input");
	public static final By toInputLocator = By.xpath("//div[@data-testid='to-testID-destination']//input");

	public static final By airportSearchLocator = By.xpath("//input[contains(@placeholder,'Enter airport code/city')]");
	// ("//div[@data-testid='to-testID-origin']//input");
	public static final By selectFromAirportLocator = By.xpath("//div[contains(text(), " + fromAirportName + "')]");
	public static final By selectToAirportLocator = By.xpath("//div[contains(text(), " + toAirportName + "')]");
	public static final By passengerButton = By.xpath("//div[contains(text(), 'Passengers')]//following-sibling::div");
	// ("//div[contains(text(), 'Adult')]");
	// ("//div[@data-testid='home-page-travellers']");
	public static final By addOneAdult = By.xpath("//div[@data-testid='home-travellers-adult-" + adultCount + "']");
	// ("//div[@data-testid='Adult-testID-plus-one-cta']");

	public static final By doneButton = By.xpath("//div[text()='Done']");
	public static final By searchButton = By.xpath("//div[@data-testid='home-page-flight-cta']");
	public static final By flightsList = By.id("onward-flight-container");

	String flightName = null;
	BrowserUtility browserUtility = new BrowserUtility();

	static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	
	protected static JavascriptExecutor js = (JavascriptExecutor) driver;

	// Default constructor (no driver assignment needed because we use
	// BaseTest.driver)
	public SpicejetCalenderPageObjects2(WebDriver driver) {
		this.driver = driver;
		// no-op
	}
	// Actions use BaseTest helper methods (clickOn, sendKeysInput, etc.)
	public void enterFromToDetails(String from, String to) {
		// clicking and typing via BaseTest helpers ensures waits are used correctly
		browserUtility.clickOn(fromInputLocator);
		browserUtility.clearData(fromInputLocator);
		browserUtility.sendKeysInput(fromInputLocator, from);

		browserUtility.clickOn(toInputLocator);
		browserUtility.clearData(toInputLocator);
		browserUtility.sendKeysInput(toInputLocator, to);
	}
	
	// Scrolls the calendar until the given month-year element exists, then returns it
	private WebElement scrollUntilMonthVisible(WebDriver driver, By monthLocator) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Try up to 15 times to scroll down
	    for (int i = 0; i < 15; i++) {

	        // 1. Check if month is present
	        List<WebElement> months = driver.findElements(monthLocator);
	        if (!months.isEmpty()) {
	            WebElement month = months.get(0);
	            js.executeScript("arguments[0].scrollIntoView(true);", month);
	            return month;
	        }

	        // 2. If not present yet, scroll the page / calendar down a bit
	        //    (SpiceJet hooks scroll on the page body for the calendar)
	        driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);

	        // Small pause so the site can load next months
	        try {
	            Thread.sleep(400);
	        } catch (InterruptedException e) {
	            // ignore
	        }
	    }

	    throw new RuntimeException("Month not found: " + monthLocator.toString());
	}

	public void selectDate2() {

	    // ===== Departure =====
	    WebElement depMonth = scrollUntilMonthVisible(driver, depatureMonthLocator);
	    WebElement depDayElement = depMonth.findElement(depatureDay);
	    depDayElement.click();

	    // ===== Re-open Return date calendar =====
	    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	    driver.findElement(calenderIconLocator2).click();

	    // ===== Return =====
	    WebElement retMonth = scrollUntilMonthVisible(driver, returnMonthLocator);
	    WebElement retDayElement = retMonth.findElement(returnDay);
	    retDayElement.click();
	    System.out.println(driver.findElement(calenderIconLocator2).getText());
	}
	
	public void selectDate() {
		WebElement date1 =browserUtility.scrollIntoElement(driver, depatureMonthLocator);
		date1.findElement(depatureDay).click();
		browserUtility.scrollIntoElementClick(driver, calenderIconLocator2);
		WebElement date2 =browserUtility.scrollIntoElement(driver, returnMonthLocator);
		date2.findElement(returnDay).click();
	}
	public void openPassengerPopupAndAddAdult() {
		js.executeScript("window.scrollTo(0, 0);");
		browserUtility.clickOn(passengerButton);
		browserUtility.isElementShowed(passengerButton);
	    int desiredAdults = Integer.parseInt(adultCount);
	    if (desiredAdults == 1) {
	    	 
	    	browserUtility.scrollIntoElementClick(driver, doneButton);
	        
	    }else {
	    	browserUtility.scrollIntoElement(driver, addOneAdult);
	    	browserUtility.clickOn(doneButton);
	    }
	}
	public void openPassengerPopupAndAddAdult1() {
		browserUtility.scrollIntoElementClick(driver, passengerButton);
		System.out.println(browserUtility.isElementShowed(passengerButton));
		browserUtility.scrollIntoElement(driver, addOneAdult);
		System.out.println(browserUtility.isElementShowed(doneButton));
		browserUtility.clickOn(doneButton);
		
		
	}

	public void clickSearch() {
		browserUtility.clickOn(searchButton);
	}

	public static void waitForpageToLoad(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			// Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("page time out");
		}
	}

	public void printFlights() {
		// use the shared driver from BaseTest
		List<WebElement> flights = null;
		try {
			flights = driver.findElements(flightsList);
		} catch (Exception e) {
			System.out.println("Error finding flights list: " + e.getMessage());
		}

		if (flights == null || flights.isEmpty()) {
			System.out.println("No Flights Found");
			return;
		}

		for (WebElement flight : flights) {
			try {
				List<WebElement> flightNames = flight.findElements(By.id("aircraft-no"));
				for (WebElement Name : flightNames) {

					flightName = Name.getText();
					String price = flight.findElement(By.id("selected-onward-container")).getText();
					System.out.println("Flight: " + flightName + " | Price: " + price);
				}

			} catch (Exception e) {
				System.out.println("Problem reading flight details: " + e.getMessage());
			}
		}
	}
}
