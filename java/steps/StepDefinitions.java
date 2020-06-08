package steps;

import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Ordering;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class StepDefinitions {
	
	public WebDriver driver;
	public Actions action;
	public WebDriverWait wait;	

	
	@Given("Launch the Browser") 
	public void launchBrowser(){
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver83.exe");
		System.setProperty("webdriver.chrome.silentOutput","true");
    
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--start-maximized");
		
		driver = new ChromeDriver(options);
	}
	
	@Given("Set the Timeouts") 
	public void timeOut() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Given("Load the URL") 
	public void loadURL() {
		driver.get("https://www.carwale.com/");
	}
	
	@Given("Click used car")
	public void clickUsed() {
		driver.findElement(By.xpath("//li[contains(text(),'Used')]")).click();
	}

	@Given("Select the City as Chennai") 
	public void selectCity() {
		action = new Actions(driver);
		WebElement city = driver.findElement(By.id("usedCarsList"));
		action.moveToElement(city).click().sendKeys("Chennai").build().perform();
		driver.findElement(By.xpath("//a[@cityname='chennai,tamilnadu']")).click();
	}

	@Given("Select budget min 8L and max 12L and Click") 
	public void budgetMinMax() throws InterruptedException{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@id='minPriceList']//li[contains(text(),'8 Lakh')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@id='maxPriceList']//li[contains(text(),'12 Lakh')]")).click();
		driver.findElement(By.id("btnFindCar")).click();
	}

	@Given("Select Cars with Photos under Only Show Cars With") 
	public void carWithPic() {
		driver.findElement(By.name("CarsWithPhotos")).click();
	}

	@Given("Select Manufacturer as Hyundai and Creta") 
	public void selectCar(){
		JavascriptExecutor click =(JavascriptExecutor) driver;
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//li[@data-manufacture-en=\"Hyundai\"]")));
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='Creta'] ")));
	}

	@Given("Select Fuel Type as Petrol") 
	public void fuelType() {
		JavascriptExecutor click =(JavascriptExecutor) driver;
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//h3[contains(text(),'Fuel Type')]")));
		click.executeScript("arguments[0].click();", driver.findElement(By.name("Petrol")));		
		driver.findElement(By.xpath("//div[contains(@class,\"coachmark filters-coachmark hide\")]/p[4]/a")).click();
	}

	@Given("Select Best Match as KM: Low to High") 
	public void bestMatch() {
		WebElement bestmatch = driver.findElement(By.id("sort"));
		Select sortcars = new Select(bestmatch);
		sortcars.selectByVisibleText("KM: Low to High");
	}

	@Given("Validate the Cars are listed with KMs Low to High") 
	public void validateSort() {
		List<WebElement> carDetails = driver.findElements(By.xpath("//span[contains(@class,'slkms vehicle-data__item')]"));
		
		List<Integer> usList = new ArrayList<Integer>();
		List<Integer> sList = new ArrayList<Integer>();
		
		for (int i = 0; i < carDetails.size(); i++) {
			String km = carDetails.get(i).getText().replaceAll("\\D", "");
			int listInt = Integer.parseInt(km);
			usList.add(listInt);
		}
		Collections.sort(usList);
		sList.addAll(usList);
		
		boolean validate = Ordering.natural().isOrdered(sList);		
		if (validate == true) {
			System.out.println("List is sort based on the KM: Low to High");
		} else {
			System.out.println("List was not sorted based on the KM: Low to High");
		}
	
	}
	
	@Given("Add the least KM ran car to Wishlist")
	public void addLeastCar() {
		driver.findElement(By.xpath("(//span[contains(@class,'shortlist-icon')])[1]")).click();
	}
	
	@Given("Go to Wishlist and Click on More Details")
	public void checkWishlist() throws InterruptedException {
		JavascriptExecutor click =(JavascriptExecutor) driver;
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//li[@data-action='ShortList&CompareWindow_Click']")));
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='More details »']")).click();
	}
	
	
	@When("Print all the details under Overview in the Same way as displayed in application")
	public void OverviewDetails() {	
		
		Set<String> windowHandle = driver.getWindowHandles();
		List<String> windows = new ArrayList<String>(windowHandle);
		driver.switchTo().window(windows.get(1));
		
		List<WebElement> overviews = driver.findElements(By.xpath("//div[@id=\"overview\"]//li/div[1]"));
		List<WebElement> features = driver.findElements(By.xpath("//div[@id=\"overview\"]//li/div[2]"));
		
		Map<String, String> ovrDetails = new LinkedHashMap<String, String>();
		for (int i = 0; i < overviews.size(); i++) {
			String ovrView = overviews.get(i).getText();
			String ftur = features.get(i).getText();
			ovrDetails.put(ovrView, ftur);
		}
		
		for (Entry<String, String> eachEntry : ovrDetails.entrySet()) {
			System.out.println(eachEntry.getKey()+" = "+eachEntry.getValue());
		}
		
	}
	
	@Then("Close the browser")
	public void closeBrowser() {
		driver.close();
		driver.quit();	
	}
}
