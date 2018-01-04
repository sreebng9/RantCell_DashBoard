package implimentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class GlueCode {
	WebDriver driver;
	public Properties prop ;
	public String timeStamp ;
	
	@Before
	public void intialisation(){
		try {
			FileInputStream fi=new FileInputStream("src\\test\\resources\\locators.properties");
			FileInputStream fii=new FileInputStream("src\\test\\resources\\registrationDetails.properties");
			
			prop=new Properties();
			prop.load(fi);
			prop.load(fii);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\Madan Raj\\\\Downloads\\\\chromedriver.exe");
		driver=new ChromeDriver();	
		driver.manage().window().maximize();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	@After
	public void error(Scenario s) throws IOException{
		if (s.isFailed()){
			Reporter.addScreenCaptureFromPath(takeScreenShot());
			 //System.out.println(takeScreenShot());
			 Reporter.addScreenCast(takeScreenShot());
		}
	}	
	public void addScreenShot() throws IOException{
		Reporter.addScreenCaptureFromPath(takeScreenShot());		
		 Reporter.addScreenCast(takeScreenShot());
	}
	
	public String takeScreenShot() throws IOException{
		TakesScreenshot f=(TakesScreenshot)driver;
		File source=f.getScreenshotAs(OutputType.FILE);
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String destination=System.getProperty("user.dir")+"\\ScreenShots\\"+timeStamp+".png";
		File dest=new File(destination);
		FileUtils.copyFile(source, dest);
		return destination;
	}
	
	public By attribute(String prop)
	{
		String[] locator=prop.split(":");
		if (locator[0].contains("id"))
		return By.id(locator[1]);
	else if (locator[0].contains("name")) {
		return By.name(locator[1]);
	}
	else if (locator[0].contains("xpath")) {
		return By.xpath(locator[1]);
	}
	else if (locator[0].contains("css")) {
		return By.cssSelector(locator[1]);
	}
	else if (locator[0].contains("linkText")) {
		return By.linkText(locator[1]);
	}
	else if (locator[0].contains("class")) {
		return By.className(locator[1]);
	}
	else
		System.out.println(locator[0] +" is Invalid locator, Please write valid locator");
		return null;
		
	}
	private void enterText(String string, String arg1) {
		driver.findElement(attribute(prop.getProperty(string))).clear();
		driver.findElement(attribute(prop.getProperty(string))).sendKeys(arg1);		
	}

	private void click(String string) {
		driver.findElement(attribute(prop.getProperty(string))).click();
		
	}
	
	@SuppressWarnings("deprecation")
	private void verifyText(String string, String string2) {
		//Assert.assertTrue(driver.findElement(attribute(prop.getProperty(string))).getText().contains(string2));
		Assert.assertEquals(string2, driver.findElement(attribute(prop.getProperty(string))).getText());		
	}

	private void getText(String string) {
		System.out.println(driver.findElement(attribute(prop.getProperty(string))).getText());
	}

	@Given("^I am on RantCell pre-login page$")
	public void i_am_on_RantCell_pre_login_page() throws Throwable {		
			if(driver!=null)
			driver.get("https://demo.rantcell.com/");
	}

	@When("^I enter username \"([^\"]*)\" and I enter password \"([^\"]*)\"$")
	public void i_enter_username_and_I_enter_password(String arg1, String arg2) throws Throwable {
	   click("loginButton");
		//enterText("login",arg1);
	   enterText("userName",arg1);
	   enterText("password",arg2);
	   
	}


	@When("^I click on login button$")
	public void i_click_on_login_button() throws Throwable {
		
		click("login");		
		addScreenShot();
		Thread.sleep(15000);	
	}

	@Then("^I should be logged onto the RantCell Home page$")
	public void ishould_be_logged_onto_the_RantCell_Home_page() throws Throwable {
	    verifyText("loginVerify", "Dashboard");
		
	}
	
	@When("^I get the basic details$")
	public void I_get_the_basic_details() throws Throwable {
	   //waitUntil("TotalTestConducted");	      
	   getText("RemainingTestMinutes");
	   getText("RegisterdDevices");
	   getText("TotalTestConducted");
	   getText("DetectedNetworks");	
	   addScreenShot();	   
		}
	


	@When("^I click on Registerd devices for more information$")
	public void I_click_on_Registerd_devices_for_more_information() throws InterruptedException, IOException{
		 Thread.sleep(10000);
		 click("RegisterdDevicesMoreinfo");
		   Thread.sleep(3000);
		   addScreenShot();
		  
		   JavascriptExecutor jse = (JavascriptExecutor)driver;
		   jse.executeScript("window.scrollBy(0,500)", "");
		  }
	
	@When("^I click on detected networks for more information$")
	public void I_click_on_detected() throws InterruptedException, IOException{
		 Thread.sleep(15000);
		 click("DetectedNetworksMoreinfo");
		   Thread.sleep(10000);
		   addScreenShot();
		  
		   JavascriptExecutor jse = (JavascriptExecutor)driver;
		   jse.executeScript("window.scrollBy(0,500)", "");
		  }
	@When("^I get the list of detected networks$")
	public void I_get_th() throws Throwable {
		int i=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr")).size();
		   List<WebElement> ele=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr"));
		   System.out.println("No of networks detected are "+i);
		   File file = new File("D:\\Selenium\\RantCell_Cucumber\\Log\\DetectedNetworks.txt");
		   
		   WriteArrayToFile(ele,file);
		   
		   for (WebElement webElement : ele) {
			System.out.println(webElement.getText());
		}	  	   
		}
	
	@When("^I get the list of devices registerd$")
	public void I_get_the() throws Throwable {
		int i=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr")).size();
		   List<WebElement> ele=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr"));
		   System.out.println("No of devices registerd are "+i);
		   File file = new File("D:\\Selenium\\RantCell_Cucumber\\Log\\RegisterdDevices.txt");
		   WriteArrayToFile(ele,file);
		   
		   for (WebElement webElement : ele) {
			System.out.println(webElement.getText());
		}	  	   
		}
	
	@When("^I logoff from the RantCell$")
	public void I_logoff() throws Throwable {
	   click("customerName");
	   click("logOff");   
		}
	
	@When("^I click on account settings then account settings page shuld be displayed$")
	public void I_logof() throws Throwable {
	   click("customerName");
	   click("accountSettings");
	   Thread.sleep(25000);
	   //waitUntillTextToPresent("changePasswordtitleVerify");
		}
	
	
	private void waitUntillTextToPresent(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 30);		 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(attribute(string))));
	
		
	}


	@When("^I click on change password then cahnge password page should be displayed$")
	public void i_click_on_change_password_then_cahnge_password_page_should_be_displayed() throws Throwable {
	    click("changePassword");
	    Thread.sleep(10000);
	    //verifyText("changePasswordtitleVerify", " Change Password");
	    
	}

	@When("^I input current passord \"([^\"]*)\" and new password \"([^\"]*)\" and click on update passord$")
	public void i_input_current_passord_and_new_password_and_click_on_update_passord(String arg1, String arg2) throws Throwable {
	    enterText("oldPassword", arg1);
	    enterText("newPassword", arg2);
	    enterText("confNewPassword", arg2);
	    click("upDatePasswordButton");
	    
	}

	@Then("^new password should be updated$")
	public void new_password_should_be_updated() throws Throwable {
	    System.out.println("New Password updated");
	}

	@When("^I try to login to Rantcell with updated credentials, username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_try_to_login_to_Rantcell_with_updated_password(String arg1, String arg2) throws Throwable {
		click("customerName");
		   click("logOff"); 
		   Thread.sleep(10000);
		   click("loginButton");
			
		   enterText("userName",arg1);
		   enterText("password",arg2);
		   click("login");		
			addScreenShot();
			Thread.sleep(10000);	
		   
	}

	@Then("^I should be logged in successfully$")
	public void i_should_be_logged_in_successfully() throws Throwable {
	    
	}
	
	
	
	
	public void WriteArrayToFile(List<WebElement> ele, File file) throws IOException{
		
		 if (!file.exists()) {
			 file.createNewFile();
         }
	        FileWriter writer = new FileWriter(file);
	        int size = ele.size();
	        for (int i=0;i<size;i++) {
	            String str = ele.get(i).getText();
	            writer.write(str);
	            if(i < size-1)//This prevent creating a blank like at the end of the file**
	                writer.write("\n");
	        }
	        writer.close();
	    }
	
	private void waitUntil(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);		 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(attribute(string))));
	}
	
	@When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\" into admin credentials$")
	public void i_enter_username_and_password_into_admin_credentials(String arg1, String arg2) throws Throwable {
	    
	click("adminLogin");
	
	   enterText("userName",arg1);
	   enterText("password",arg2);	
	}

	@When("^I click on User Management menu located on left side$")
	public void i_click_on_User_Management_menu_located_on_left_side() throws Throwable {
	    click("userManagement");
	}

	@Then("^User information page should be displayed$")
	public void user_information_page_should_be_displayed() throws Throwable {
	    //verifyText("addUser", "Add User ");
	   	}

	@When("^I click on Add user option$")
	public void i_click_on_Add_user_option() throws Throwable {
	    click("addUser");
	}

	@When("^I enter all the details for required for registration and click on register$")
	public void i_enter_all_the_details_for_required_for_registration_and_click_on_register() throws Throwable {
	    enterText("firstName", prop.getProperty("FirstName"));
	    enterText("lastName", prop.getProperty("LastName"));
	    enterText("email", prop.getProperty("Email"));
	    enterText("noOfLicense", prop.getProperty("NoOfLicense"));
	    enterText("passwordreg", prop.getProperty("Password"));
	    enterText("confPassword", prop.getProperty("ConfirmPassword"));
	    selectByVisibleText("country", prop.getProperty("Country"));
	    enterText("company", prop.getProperty("Company"));
	    enterText("pincode", prop.getProperty("Pincode"));
	    click("register");
	    
	}

	private void selectByVisibleText(String string, String string2) {
		Select s=new Select(driver.findElement(attribute(prop.getProperty(string))));
		s.selectByVisibleText(string2);
		
	}


	@Then("^user should be successfully registerd$")
	public void user_should_be_successfully_registerd() throws Throwable {
	    Thread.sleep(5000);
	    click("customerName");
		   click("logOff");   
	    
	}
	
	@When("^I click on address update then change address page should be displayed$")
	public void i_click_on_address_update_then_change_address_page_should_be_displayed() throws Throwable {
	    
		driver.findElement(By.xpath("//a[@href='#/app/changeAddress']")).click();
		Thread.sleep(5000);
	}

	@When("^I input Address, pincode, country that needs to be updated and click on update address button$")
	public void i_input_Address_pincode_country_that_needs_to_be_updated_and_click_on_update_address_button() throws Throwable {
		enterText("change_address", prop.getProperty("Change_Address"));
		enterText("change_pincode", prop.getProperty("Change_pincode"));
		selectByVisibleText("change_country", prop.getProperty("Change_Country"));
		addScreenShot();
		click("changeAddressButton");
		
	}

	@Then("^address should be updated in the customer details$")
	public void address_should_be_updated_in_the_customer_details() throws Throwable {
	    Thread.sleep(10000);
	    System.out.println("Address has been updated");
	    addScreenShot();
	}

	@When("^I click on phone number update then change mobile number page should be displayed$")
	public void i_click_on_phone_number_update_then_change_mobile_number_page_should_be_displayed() throws Throwable {
	    click("updateMobileNolink");
	    Thread.sleep(8000);
	}

	@When("^I input new mobile number that needs to be updated and click on update mobile number button$")
	public void i_input_new_mobile_number_that_needs_to_be_updated_and_click_on_update_mobile_number_button() throws Throwable {
	    enterText("newMobileNo", prop.getProperty("NewMobileNo"));
	    click("updateMobileNoButton");
	    Thread.sleep(5000);
	}

	@Then("^mobile number should be updated in the customer details$")
	public void mobile_number_should_be_updated_in_the_customer_details() throws Throwable {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
		addScreenShot();
	}
	
	
	@Then("^I click on Remote Test and I click on Add test group$")
	public void i_click_on_Remote_Test_and_I_click_on_Add_test_group() throws Throwable {
	    click("remoteTest");
	    Thread.sleep(3000);
	    click("addTestGroup");
	    
	}

	public static String groupName;
	@When("^I enter group name as \"([^\"]*)\" and click on next button$")
	public void i_enter_group_name_as_and_click_on_next_button(String arg1) throws Throwable {
		groupName=arg1;
		enterText("enterGroupName", arg1);
	    click("nextButton");
	    Thread.sleep(4000);
	    if(driver.findElement(By.id("Groupform")).isDisplayed()){
			groupName=arg1+"i";	
			driver.findElement(By.name("GroupName")).sendKeys(groupName);
			driver.findElement(By.xpath("//*[@id='nextBtn']")).click();
	    }
	      
	}

	@Then("^List of devices page should be displayed$")
	public void list_of_devices_page_should_be_displayed() throws Throwable {
		Thread.sleep(3000);
	   List<WebElement> noOfDevices= driver.findElements(By.xpath("//tr[@ng-repeat='device in devices']"));
	   for (WebElement webElement : noOfDevices) {
		System.out.println("No of devices are: "+webElement.getText());
	}
	   
	}

	@When("^I select the device and click on add button$")
	public void i_select_the_device_and_click_on_add_button() throws Throwable {
	    click("listDevicesCheckBox");
	    click("addButton");
	    
	}
	public static int i=0;
	@Then("^test group name should be displayed in the list of groups$")
	public void test_group_name_should_be_displayed_in_the_list_of_groups() throws Throwable {
		Thread.sleep(5000);
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
	   System.out.println("testGroupList no: " +testGroupList.size());
		
	    for (WebElement webElement : testGroupList) {
	    	++i;
			if(webElement.getText().contains(groupName)){				
				webElement.click();
			}
		}
	    System.out.println("i value is: "+i);
	}

	@When("^I select the test group that we created and check for devices$")
	public void i_select_the_test_group_that_we_created_and_check_for_devices() throws Throwable {
		Thread.sleep(3000);
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		System.out.println("I select the test" +list.size());
		for (WebElement webElement : list) {
			System.out.println(webElement.getText());
		}
		
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Check Devices")){
				webElement.click();
			}
		}
	}

	@Then("^I can see the list of devices and their status$")
	public void i_can_see_the_list_of_devices_and_their_status() throws Throwable {
		Thread.sleep(3000);
		addScreenShot();
		}

	@Then("^I can see detailed details by clicking on show details link$")
	public void i_can_see_detailed_details_by_clicking_on_show_details_link() throws Throwable {
		Thread.sleep(5000);
		System.out.println(attribute(prop.getProperty("showDetails")));
		click("showDetails");
		
		addScreenShot();
		click("xButton");
		Thread.sleep(3000);
		click("closeButton");
	}

	@When("^I click on Run Test then I fill the details required for ping test and click on start button$")
	public void i_click_on_Run_Test_then_I_fill_the_details_required_for_ping_test_and_click_on_start_button() throws Throwable {
		System.out.println("I click on Run Test"+i);
		Thread.sleep(5000);
		
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
		   System.out.println("testGroupList no: " +testGroupList.size());
			
		    for (WebElement webElement : testGroupList) {
		    	
				if(webElement.getText().contains(groupName)){				
					webElement.click();
				}
			}
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Run Test")){
				webElement.click();
			}
		}
		  enterText("testName", prop.getProperty("TestName"));
		    enterText("iterations", prop.getProperty("Iterations"));
		    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
		    
		    click("pingTestCheckBox");
		    Thread.sleep(4000);
		    enterText("host", prop.getProperty("Host"));
		    click("hostOKbutton");
		    Thread.sleep(2000);
		    
		    driver.findElement(By.xpath("//*[@id='scheduledtask']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(10000);
		    addScreenShot();
		    List<WebElement> list1=driver.findElements(By.xpath("//*[@id='deliveryReportRefreshModal']/div/div/div[2]/table/tbody/tr"));
			for (WebElement webElement : list1) {
				System.out.println(webElement.getText());
			}
			
			driver.findElement(By.xpath("//*[@id='deliveryReportModal']/div/div/div[3]/a")).click();
		
	}

	@Then("^I can see the staus in table view format\\.$")
	public void i_can_see_the_staus_in_table_view_format() throws Throwable {
	  
		Thread.sleep(2000);
		//driver.navigate().to(driver.getCurrentUrl());
		driver.navigate().refresh();
		
		driver.findElement(By.id("reloadTrack")).click();
		
		List<WebElement> trackingList=driver.findElements(By.xpath("//ul[@class='treeview-menu menu-open']/li"));
		
		for (WebElement webElement : trackingList) {
			if (webElement.getText().contains(groupName)){
				webElement.click();
			}
		}
	}
	
	
	
	@When("^I click on Run Test then I fill the details required for speed test and click on start button$")
	public void i_click_on() throws Throwable {
		
		Thread.sleep(5000);
		
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
		   System.out.println("testGroupList no: " +testGroupList.size());
			
		    for (WebElement webElement : testGroupList) {
		    	
				if(webElement.getText().contains(groupName)){				
					webElement.click();
				}
			}
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Run Test")){
				webElement.click();
			}
		}
		  enterText("testName", prop.getProperty("TestName"));
		    enterText("iterations", prop.getProperty("Iterations"));
		    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
		    
		    click("speedTestCheckBox");
		    Thread.sleep(3000);
		    
		    driver.findElement(By.id("uploadtest")).click();
		   
		  driver.findElement(By.xpath("//*[@id='speedtest']/div/div/div[3]/a[1]")).click();
		    Thread.sleep(2000);
	
		    driver.findElement(By.xpath("//*[@id='scheduledtask']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(10000);
		    addScreenShot();
		    List<WebElement> list1=driver.findElements(By.xpath("//*[@id='deliveryReportRefreshModal']/div/div/div[2]/table/tbody/tr"));
			for (WebElement webElement : list1) {
				System.out.println(webElement.getText());
			}
			
			driver.findElement(By.xpath("//*[@id='deliveryReportModal']/div/div/div[3]/a")).click();
		
	}
	
	@When("^I enter mandetory run test details and check on sms test$")
	public void i_click_on_() throws Throwable {
		
		Thread.sleep(5000);
		
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
		   System.out.println("testGroupList no: " +testGroupList.size());
			
		    for (WebElement webElement : testGroupList) {
		    	
				if(webElement.getText().contains(groupName)){				
					webElement.click();
				}
			}
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Run Test")){
				webElement.click();
			}
		}
		  enterText("testName", prop.getProperty("TestName"));
		    enterText("iterations", prop.getProperty("Iterations"));
		    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
		    
		    click("smsTestCheckBox");
		    Thread.sleep(3000);
		    
		    
	}
	
	
	@When("^I enter the B party phone number as \"([^\"]*)\" and wait duration \"([^\"]*)\" seconds and proceed to start test$")
	public void i_click_on_a(String arg1, String arg2) throws Throwable {
		
			driver.findElement(By.id("smsnumber")).sendKeys(arg1);
			driver.findElement(By.id("smsduration")).sendKeys(arg2);
			
			  // ok button
			driver.findElement(By.xpath("//*[@id='smstest']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(2000);
	
		    
		    driver.findElement(By.xpath("//*[@id='scheduledtask']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(10000);
		    addScreenShot();
		    List<WebElement> list1=driver.findElements(By.xpath("//*[@id='deliveryReportRefreshModal']/div/div/div[2]/table/tbody/tr"));
			for (WebElement webElement : list1) {
				System.out.println(webElement.getText());
			}
			//close button
			driver.findElement(By.xpath("//*[@id='deliveryReportModal']/div/div/div[3]/button")).click();
		driver.findElement(By.id("refreshDashboard")).click();
		Thread.sleep(3000);
		driver.navigate().refresh();
		Thread.sleep(1000);
		driver.navigate().refresh();
		
		boolean s=driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[3]")).getText().contains(prop.getProperty("TestName"));
		Assert.assertTrue(s);
		System.out.println("Latest performed test present on Table view");
		
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
		System.out.println("Status oflatest excecuted campaign : "+driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[23]")).getText());
		addScreenShot();
		
	}
	
	@When("^I enter mandetory run test details and check on call test$")
	public void i_click_on_i() throws Throwable {
		
		Thread.sleep(5000);
		
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
		   System.out.println("testGroupList no: " +testGroupList.size());
			
		    for (WebElement webElement : testGroupList) {
		    	
				if(webElement.getText().contains(groupName)){				
					webElement.click();
				}
			}
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Run Test")){
				webElement.click();
			}
		}
		  enterText("testName", prop.getProperty("TestName"));
		    enterText("iterations", prop.getProperty("Iterations"));
		    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
		    
		    click("callTestCheckBox");
		    Thread.sleep(3000);
		    
		    
	}
	
	
	@When("^I enter the B party phone number as \"([^\"]*)\" and call duration \"([^\"]*)\" seconds and proceed to start test$")
	public void i_click_on_a_(String arg1, String arg2) throws Throwable { 
		
			driver.findElement(By.id("number")).sendKeys(arg1);
			driver.findElement(By.id("duration")).clear();
			driver.findElement(By.id("duration")).sendKeys(arg2);
			
			  // ok button
			driver.findElement(By.xpath("//*[@id='calltest']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(2000);
	
		    
		    driver.findElement(By.xpath("//*[@id='scheduledtask']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(10000);
		    //addScreenShot();
		    List<WebElement> list1=driver.findElements(By.xpath("//*[@id='deliveryReportRefreshModal']/div/div/div[2]/table/tbody/tr"));
			for (WebElement webElement : list1) {
				System.out.println(webElement.getText());
			}
			//close button
			driver.findElement(By.xpath("//*[@id='deliveryReportModal']/div/div/div[3]/button")).click();
		driver.findElement(By.id("refreshDashboard")).click();
		Thread.sleep(3000);
		driver.navigate().refresh();
		Thread.sleep(1000);
		driver.navigate().refresh();
		
		boolean s=driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[3]")).getText().contains(prop.getProperty("TestName"));
		Assert.assertTrue(s);
				
		System.out.println("Status oflatest excecuted campaign : "+driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[23]")).getText());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
		addScreenShot();
		
	}
	
	@When("^I enter mandetory run test details and check on http test$")
	public void i_click_on_i_() throws Throwable {
		
		Thread.sleep(5000);
		
		List<WebElement> testGroupList=driver.findElements(attribute(prop.getProperty("createdTestGroupsList")));
		   System.out.println("testGroupList no: " +testGroupList.size());
			
		    for (WebElement webElement : testGroupList) {
		    	
				if(webElement.getText().contains(groupName)){				
					webElement.click();
				}
			}
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Run Test")){
				webElement.click();
			}
		}
		  enterText("testName", prop.getProperty("TestName"));
		    enterText("iterations", prop.getProperty("Iterations"));
		    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
		    
		    click("httpTestCheckBox");
		    Thread.sleep(3000);
		    
		    
	}
	
	
	@When("^I fill the mandetory fields \"([^\"]*)\" in http speed test and proceed to start test$")
	public void i_click_on_a_a(String arg1) throws Throwable { 
		
			driver.findElement(By.id("defaulturl")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("http")).clear();
			driver.findElement(By.id("http")).sendKeys(arg1);
			
			  // ok button
			
			driver.findElement(By.xpath("//*[@id='httpsubmitBtn']")).click();
		    Thread.sleep(2000);
	
		    
		    driver.findElement(By.xpath("//*[@id='scheduledtask']/div/div/div[3]/div/a[1]")).click();
		    Thread.sleep(10000);
		    //addScreenShot();
		    List<WebElement> list1=driver.findElements(By.xpath("//*[@id='deliveryReportRefreshModal']/div/div/div[2]/table/tbody/tr"));
			for (WebElement webElement : list1) {
				System.out.println(webElement.getText());
			}
			//close button
			driver.findElement(By.xpath("//*[@id='deliveryReportModal']/div/div/div[3]/button")).click();
		driver.findElement(By.id("refreshDashboard")).click();
		Thread.sleep(3000);
		driver.navigate().refresh();
		Thread.sleep(1000);
		driver.navigate().refresh();
		
		boolean s=driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[3]")).getText().contains(prop.getProperty("TestName"));
		Assert.assertTrue(s);
				
		System.out.println("Status oflatest excecuted campaign : "+driver.findElement(By.xpath("//*[@id='tableView']/div[2]/div/div/div/table/tbody/tr[1]/td[23]")).getText());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
		addScreenShot();
		
	}
	
	
	@When("^I select the test group that we created and check for Live Tracking$")
	public void i_select_the_test_that_we_created_and_check_for_devices() throws Throwable {
		Thread.sleep(3000);
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		System.out.println("I select the test" +list.size());
		for (WebElement webElement : list) {
			System.out.println(webElement.getText());
		}
		
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Live Tracking")){
				webElement.click();
			}
		}
	}
		
	@When("^And mapview is captured$")
		public void i_s_() throws Throwable {
			addScreenShot();
			}
	
	@When("^I select the test group that we created and check for Schedule Test$")
	public void i_select() throws Throwable {
		Thread.sleep(3000);
		List<WebElement> list=driver.findElements(By.xpath("(//ul[@id='responsivedropdownmenu'])["+i+"]/li/a"));
		System.out.println("I select the test" +list.size());
		for (WebElement webElement : list) {
			System.out.println(webElement.getText());
		}
		
		for (WebElement webElement : list) {
			if(webElement.getText().contains("Schedule Test")){
				webElement.click();
			}
		}
		
		enterText("testName", prop.getProperty("TestName"));
	    enterText("iterations", prop.getProperty("Iterations"));
	    enterText("delayBetweenTests", prop.getProperty("DelayBetweenTests"));
	    
	    click("callTestCheckBox");
	    Thread.sleep(3000);
	}
	
	
}	


 
		

	
		
		
		/*try {
		    FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\ScreenShots\\"+timeStamp+"RegisterdDevices"));
		    ObjectOutputStream oos = new ObjectOutputStream(fos);   
		    oos.writeObject(ele); 
		    oos.close(); 
		} catch(Exception ex) {
		    ex.printStackTrace();
		}*/
