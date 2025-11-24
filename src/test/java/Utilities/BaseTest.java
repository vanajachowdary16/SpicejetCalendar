package Utilities;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import java.util.NoSuchElementException;

public class BaseTest {

    public final static String PROJECT_PATH = System.getProperty("user.dir") + "/";
    public final static String BROWSER = "chrome";

    // shared browser for tests + page objects that extend BaseTest
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor js = (JavascriptExecutor) driver;

    @BeforeMethod
    public static void launchBrowser() {
        // initialize the driver here (ensure chromedriver is on PATH or set system property)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // initialize explicit wait AFTER driver exists
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    
    public static WebDriver getDriver() {
		return driver;
	}

    @AfterMethod
    public static void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) { }
        }
    }

    // Helper: JS executor (created after driver exists)
    protected static JavascriptExecutor getJs() {
        return (JavascriptExecutor) driver;
    }

    // Basic helpers (use the shared static wait)
    public static void clickOn(By locator) {
        
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
public static void clickOnLocatedElement(By locator) {
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public static void clearData(By locator) {
      
        wait.until(ExpectedConditions.elementToBeClickable(locator)).clear();
    }
 public static String getText(By locator) {
        
      String str  =wait.until(ExpectedConditions.elementToBeClickable(locator)).getText();
      return str;
        
    }

    public static void sendKeysInput(By locator, String testData) {
     
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        //element.clear();
        element.sendKeys(testData);
    }

    public static void explicitWait(By locator) {
              wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }



    // find element helper (not required but kept)
    public static WebElement findElementByLocator(By locator) {
       
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            explicitWait(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator);
        }
        return element;
    }
    public static WebElement scrollIntoElement(WebDriver driver,By locator) {
    	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    	
    	return element;
    	
    }
    
    public static void scrollIntoElementClick(WebDriver driver,By locator) {
    	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	
    	
    }
    public static void jsClick(WebDriver driver, WebElement element)
    {    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	//js.executeScript("arguments[0].click()", element);
    }
    // Optional helper to scroll to element using JS
    public static void jsScrollToElement(WebElement element) {
        try {
            getJs().executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception ignored) { }
    }
    public static boolean isElementShowed(By locator) {
    	WebElement located =findElementByLocator(locator);
    	if(located.isDisplayed()) {
			System.out.println("reached till " +located.getText()+ " element");
    	}
    	return true;
    }
}
