package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.BaseTest;

public class SpicejetCalenderPageObjects extends BaseTest {

    // Only By locators at class level — no WebElement or wait initialization here
	
	public static final String depature_month = "November";
	public static final String depature_day = "26";

	public static final String return_month = "December";
	public static final String return_day = "12";
	public static final By depatureDate = By.xpath("//div[contains(@data-testid, 'undefined-month-"+depature_month+"')]//div[contains(@data-testid, 'undefined-calendar-day-"+depature_day+"')]");
	public static final By returnDate = By.xpath("//div[contains(@data-testid,'undefined-month-"+return_month +"')]//div[contains(@data-testid,'calendar-day-"+return_day +"')]");
	public static final By calenderIconLocator = By.xpath("//div[contains(text(), 'Departure Date')]//following-sibling::div");
	public static final By calenderIconLocator2 = By.xpath("//div[contains(text(), 'Return Date')]//following-sibling::div");
    public static final By selectDateLocator =
        By.xpath("//div[@data-testid='undefined-month-October-2025']//following-sibling::*[contains(@data-testid,'calendar-day-1')]");
    public static final By fromInputLocator = By.xpath("//div[@data-testid='to-testID-origin']//input");
    public static final By toInputLocator = By.xpath("//div[@data-testid='to-testID-destination']//input");
    public static final By passengerButton = By.xpath("//div[@data-testid='home-page-travellers']");
    public static final By addOneAdult = By.xpath("//div[@data-testid='Adult-testID-plus-one-cta']");
    public static final By doneButton = By.xpath("//div[@data-testid='home-page-travellers-done-cta']");
    public static final By searchButton = By.xpath("//div[@data-testid='home-page-flight-cta']");
    public static final By flightsList = By.id("onward-flight-container");
    
   
    
    String flightName=null;
    
     static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // Default constructor (no driver assignment needed because we use BaseTest.driver)
    public SpicejetCalenderPageObjects(WebDriver driver) {
    	this.driver = driver;
        // no-op
    }

    // Actions use BaseTest helper methods (clickOn, sendKeysInput, etc.)
    public void enterFromToDetails(String from, String to) {
        // clicking and typing via BaseTest helpers ensures waits are used correctly
        BaseTest.clickOn(fromInputLocator);
        BaseTest.clearData(fromInputLocator);
        BaseTest.sendKeysInput(fromInputLocator, from);

        BaseTest.clickOn(toInputLocator);
        BaseTest.clearData(toInputLocator);
        BaseTest.sendKeysInput(toInputLocator, to);
    }
    
    public void selectDate() throws InterruptedException {
    	//BaseTest.clickOn(calenderIconLocator);
    	//Thread.sleep(5000);
    	BaseTest.clickOn(depatureDate);
    	BaseTest.clickOn(calenderIconLocator2);
    	BaseTest.clickOn(returnDate);
    	String selectedDate  = BaseTest.getText(calenderIconLocator2);
    	System.out.println(selectedDate);
    	
    }

    public void openPassengerPopupAndAddAdult() {
        BaseTest.clickOn(passengerButton);
        BaseTest.clickOn(addOneAdult);
        BaseTest.clickOn(doneButton);
    }

    public void clickSearch() {
        BaseTest.clickOn(searchButton);
    }
    
    
    public static void waitForpageToLoad(WebDriver driver){
		try{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			//Thread.sleep(2000);
		}catch(Exception e){
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