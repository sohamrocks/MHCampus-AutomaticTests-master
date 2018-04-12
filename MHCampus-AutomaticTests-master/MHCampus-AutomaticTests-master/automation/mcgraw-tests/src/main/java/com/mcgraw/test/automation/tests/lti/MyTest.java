package com.mcgraw.test.automation.tests.lti;

import java.text.SimpleDateFormat;
import java.util.Date;

/*import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;*/

import org.testng.annotations.Test;

//import com.mcgraw.test.automation.tests.base.BaseTest;

public class MyTest { // extends BaseTest {

	
	@Test(description = "Test")
	public void testOopsError() throws Exception {
		
		/*String text = "http://yali.tegrity.com/CustomIntegrationModules/MoodlelIntegrationService.svc";
		System.out.println(text);
		text = text.replace("MoodlelIntegrationService.svc", "MoodleConnectorPass.svc");
		System.out.println(text);*/

		/*String ltiCustomParameters = "application_type=mhcampus";
		System.out.println(ltiCustomParameters); 
		ltiCustomParameters = ltiCustomParameters + "\n" + "mhcampus_isbn=0073406732";
		System.out.println(ltiCustomParameters); */
		
		//String filePath = System.getProperty("user.dir") + "/src/res/test.pdf"; 
		//System.out.println(filePath);
		
		/*String expectedDate = "October 14, 2015";
		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy"); //October 14, 2015 
		//get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println(expectedDate);
		if(dateFormat.format(date).compareTo(expectedDate) == 0)   
			System.out.println(dateFormat.format(date));*/
		
		//String startDate = "Jun 30, 2018 at 12:30am"; //10:30a 06/30/2019 06/30/2018  Jun 30, 2017 at 2:30pm
		//String startDateExpected = "06/30/2018 10:30pm";
		/*String startDateExpected = "06/30/2018 10:30am";
		String endDateExpected = "Jun 30, 2018 at 10:30am";
		
		String startDate = "Jun 30, 2018 at 10:30am"; //Sat Jun 30 10:30:00 IDT 2018
		String andDate = "Jun 30, 2019 at 11:59pm";
		
		//SimpleDateFormat dt = new SimpleDateFormat("MMM d, yyyy 'at' hh:mmaa");
		SimpleDateFormat dt1 = new SimpleDateFormat("MMM d, yyyy 'at' hh:mmaa");
		
        Date date1 = dt1.parse(startDate);
        System.out.println(startDate);
        System.out.println(date1);
        
        SimpleDateFormat dt2 = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");
     
        Date date2 = dt2.parse(startDateExpected);
        System.out.println(startDateExpected);
        System.out.println(date2);
        
        if(date1.equals(date2)){
        	System.out.println("yes");
        }else{
        	System.out.println("no");
        }*/
		
		
		/*String startDateExpected = "Jul 11 at 4:07am";
		//Jul 11 at
		//Jul 01 at
		//Jul 1 at
		
		//SimpleDateFormat dt = new SimpleDateFormat("MMM d, yyyy 'at' hh:mmaa");
		SimpleDateFormat dt1 = new SimpleDateFormat("MMM d, yyyy 'at' hh:mmaa");
		
        Date date = new Date();
        System.out.println(startDateExpected);
        System.out.println(date.toString());
        
        String part = startDateExpected.substring(0, 6);
        if(date.toString().contains(part)){
        	System.out.println("yes");
        }else{
        	System.out.println("no");
        }*/
		
		Date date = new Date();
		StringBuilder today = new StringBuilder(date.toString());
		System.out.println("old date: " + today);		
		if(today.charAt(8) == '0'){
			today.deleteCharAt(8);
		}
		
		System.out.println("new date: " + today);		
	}
}
