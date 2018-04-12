/*package com.mcgraw.test.automation.test.CDIservices;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;

public class TC11244CreateCDIAdmin {
  @BeforeClass
  public void beforeClass() {
  }


  @Test
  public void CreateCDIadmin() {
	  WebDriver driver = new FirefoxDriver();
  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
      //String LoginText = driver.findElement(By.id("LabelInstitution")).getText();
  	Assert.verifyTrue(LoginText.contains("Institution:"));
  	    	
  }
}
*/