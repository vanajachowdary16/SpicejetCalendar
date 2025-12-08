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

public class BrowserUtility {

    public final static String PROJECT_PATH = System.getProperty("user.dir") + "/";
    public final static String BROWSER = "chrome";

    // shared browser for tests + page objects that extend BaseTest
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor js = (JavascriptExecutor) driver;

    @BeforeMethod
    public void launchBrowser() {
        // initialize the driver here (ensure chromedriver is on PATH or set system property)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // initialize explicit wait AFTER driver exists
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    
    public WebDriver getDriver() {
		return driver;
	}

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) { }
        }
    }

    // Helper: JS executor (created after driver exists)
    protected JavascriptExecutor getJs() {
        return (JavascriptExecutor) driver;
    }

    // Basic helpers (use the shared static wait)
    public void clickOn(By locator) {
        
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
public void clickOnLocatedElement(By locator) {
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void clearData(By locator) {
      
        wait.until(ExpectedConditions.elementToBeClickable(locator)).clear();
    }
 public static String getText(By locator) {
        
      String str  =wait.until(ExpectedConditions.elementToBeClickable(locator)).getText();
      return str;
        
    }

    public void sendKeysInput(By locator, String testData) {
     
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        //element.clear();
        element.sendKeys(testData);
    }

    public void explicitWait(By locator) {
              wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }



    // find element helper (not required but kept)
    public  WebElement findElementByLocator(By locator) {
       
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            explicitWait(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator);
        }
        return element;
    }
    public WebElement scrollIntoElement(WebDriver driver,By locator) {
    	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    	
    	return element;
    	
    }
    
    public void scrollIntoElementClick(WebDriver driver,By locator) {
    	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	
    	
    }
    public void jsClick(WebDriver driver, By locator)
    {    	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	//js.executeScript("arguments[0].click()", element);
    }
    // Optional helper to scroll to element using JS
    public void jsScrollToElement(WebElement element) {
        try {
            getJs().executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception ignored) { }
    }
    public boolean isElementShowed(By locator) {
    	WebElement located =findElementByLocator(locator);
    	if(located.isDisplayed()) {
			System.out.println("reached till " +located.getText()+ " element");
    	}
    	return true;
    }
}
