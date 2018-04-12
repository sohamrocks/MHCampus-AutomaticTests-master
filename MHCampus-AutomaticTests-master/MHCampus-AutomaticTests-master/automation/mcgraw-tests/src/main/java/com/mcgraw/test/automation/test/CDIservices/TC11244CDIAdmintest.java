package com.mcgraw.test.automation.test.CDIservices;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;

public class TC11244CDIAdmintest extends BaseTest {
	
	public String Adminusername;
	public String Adminpassword;
	public String Emailidadmin;
	public String Institution;
	public String Emailidcust;
	public WebDriver driver = new FirefoxDriver();
	
	@BeforeSuite
	public void TestData(){
		String CDIAdminusernameRandom = getRandomString();
    	String CDIAdminpasswordRandom = getRandomString();
    	String CDIInstitutionRandom = getRandomString();
    	Adminusername = "username" + CDIAdminusernameRandom;
    	Adminpassword = "password" + CDIAdminpasswordRandom;
    	Emailidadmin = "abcdgfg@gmail.com";
    	Emailidcust = "sturdysam@gnail.co";
        Institution = "institute" + CDIInstitutionRandom;
    	System.out.println(Adminpassword);
	}
	
    
    @BeforeTest   
    public void CreateCDIadmin () {
    	//driver.findElement(By.id("ctl00_LoginStatus1")).click();
    	//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.get("https://login-aws-qa.tegrity.com/Service/ClientServices.aspx");
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	String LoginText = driver.findElement(By.id("LabelInstitution")).getText();
    	Assert.verifyTrue(LoginText.contains("Institution:"));
        driver.findElement(By.id("TextBoxInstitution")).sendKeys("Client Services");
        driver.findElement(By.id("TextBoxUsername")).sendKeys("tata");
        driver.findElement(By.id("TextBoxPassword")).sendKeys("f6Tsp0Ne");
        driver.findElement(By.id("ButtonLogin")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        String Dashboardtext = driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_BreadCrumb1_LabelCurrent")).getText();
        Assert.verifyTrue(Dashboardtext.contains("Client services dashboard"));
    	System.out.println(Adminusername+" "+"admin");
        String createCDIAdmin ="ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_QuickLinks1_LinkButtonUsers";
        driver.findElement(By.id(createCDIAdmin)).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	
    	System.out.println(Adminusername+" "+"value");
    	System.out.println(Emailidadmin+" "+"value2");
    	
        String usernm = "ctl00_ContentPlaceHolder1_ClientServicesMain1_Users1_NewUser1_TextUserName";
        driver.findElement(By.id(usernm)).sendKeys(Adminusername);
        String passwd = "ctl00_ContentPlaceHolder1_ClientServicesMain1_Users1_NewUser1_TextBoxPassword";
        driver.findElement(By.id(passwd)).sendKeys(Adminpassword);
        String email = "ctl00_ContentPlaceHolder1_ClientServicesMain1_Users1_NewUser1_TextEmail";
        driver.findElement(By.id(email)).sendKeys(Emailidadmin);
        String Userrl = "ctl00_ContentPlaceHolder1_ClientServicesMain1_Users1_NewUser1_DropDownListUserRoles";
        Select Userrole = new Select(driver.findElement(By.id(Userrl)));
        Userrole.selectByValue("CDIAdmin");
        String Save = "ctl00_ContentPlaceHolder1_ClientServicesMain1_Users1_NewUser1_ButtonCreateUser";
        driver.findElement(By.id(Save)).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("ctl00_LoginStatus1")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);       
    	    	
    }
    
    @BeforeMethod
    public void CDIadminLogin() throws Exception{
    	driver.get("https://login-aws-qa.tegrity.com/Service/ClientServices.aspx");
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	String LoginText = driver.findElement(By.id("LabelInstitution")).getText();
    	Assert.verifyTrue(LoginText.contains("Institution:"));
        driver.findElement(By.id("TextBoxInstitution")).sendKeys("CDI Services");
        driver.findElement(By.id("TextBoxUsername")).sendKeys(Adminusername);
        driver.findElement(By.id("TextBoxPassword")).sendKeys(Adminpassword);
        driver.findElement(By.id("ButtonLogin")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        System.out.println("test1");
    	
    }
   
	 @Test(priority=1)   
	    public void VerifyDefaultCDItab(){
	    	String select = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_RadTabStripCSDashboard\"]/div/ul/li[1]/a")).getAttribute("class");
	        System.out.println("value" + " " + select);
	        String value = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_RadTabStripCSDashboard\"]/div/ul/li[1]/a")).getText();
	        System.out.println("value" + value);
	        if (select.equals("rtsLink rtsSelected") && value.equals("K12")){
	        	System.out.println("True");
	        	String CDItest = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_k12Customers1_RadGridCustomers_ctl00\"]/thead/tr[1]/th[5]/a")).getText();
	        	System.out.println(CDItest);
	        	Assert.verifyTrue(CDItest.contains("Organization Id"));
	        }
	        else {
	        	System.out.println("False");
	        	}
	    }
	 
    @Test(priority=2)
    public void CreateCDIcustomer() throws Exception{
        String CDIcust = "ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_QuickLinks1_LinkButtonCreateCDICustomer";
        driver.findElement(By.id(CDIcust)).click();
        String CreateCust = driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_BreadCrumb1_LabelCurrent")).getText();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.verifyTrue(CreateCust.contains("Create CDI customer"));        
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LinkButtonAddNewInstitution3")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewInstitute_TextBoxNewInstitution")).sendKeys(Institution);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewInstitute_ButtonSaveNewInstitution")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        //create new user
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBoxCDIDomain")).sendKeys(Institution);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBoxCDIInstitution")).sendKeys(Institution);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBoxEmailContact")).sendKeys(Emailidcust);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBoxEmailOwner")).sendKeys(Emailidcust);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ButtonCreateCustomer")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        //Following line needs to be removed
        
        driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_RadTabStripCSDashboard\"]/div/ul/li[2]/a/span/span/span")).click();
        
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MHCdICustomers1_RadGridCustomers_ctl00_ctl02_ctl02_FilterTextBox_InstitutionColumn")).sendKeys(Institution);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MHCdICustomers1_RadGridCustomers_ctl00_ctl02_ctl02_FilterTextBox_InstitutionColumn")).sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String Instite = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MHCdICustomers1_RadGridCustomers_ctl00__0\"]/td[3]")).getText();
        Assert.verifyTrue(Instite.contains(Institution));
        
             
    	
    }
    
    
    @AfterMethod
    public void Logout(){
    	driver.findElement(By.id("ctl00_LoginStatus1")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String LoginText = driver.findElement(By.id("LabelInstitution")).getText();
    	Assert.verifyTrue(LoginText.contains("Institution:"));
    	//driver.close();
    }

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
	
}
