package com.mcgraw.test.automation.ui.applications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Letter;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.service.EmailClient;

public class ImsImportApplication {
	
	@Value("${tegrity.imsimport.path}")
	public String pathToFile;	

	private EmailClient emailClient;
    private String bodyContent = "Import finished";
	Browser browser;

	public ImsImportApplication(Browser browser) {
		this.browser = browser;
	}	
	
	public EmailClient getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	
	public boolean isFileUploadSuccessfully() throws Exception {
		Logger.info("Check the file was upload successfully...");
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy"); 
		Date date = new Date();
		String today = dateFormat.format(date);
		
		List<Letter> receivedLetters = emailClient.WaitForEmailWithBodyContentArrival(bodyContent);
		for(int i=0; i<receivedLetters.size(); i++){
			Letter letter = receivedLetters.get(i);
			String dateFromLetter = dateFormat.format(letter.receivedDate);
			if(today.equals(dateFromLetter) && letter.body.contains("Import status: Success"))
				return true;
		}
		return false;
	}
	
	public void deleteMailByBodyContent() throws Exception {
		Logger.info("Delete mail, which contains date: " + bodyContent);
		emailClient.deleteEmailByBodyContent(bodyContent);
	}
}
