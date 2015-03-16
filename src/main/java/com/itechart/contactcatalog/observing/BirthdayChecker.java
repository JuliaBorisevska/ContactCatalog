package com.itechart.contactcatalog.observing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.logic.MailSender;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.template.TemplateCreator;
import com.itechart.contactcatalog.template.TemplateType;


public class BirthdayChecker extends Thread{
	private static Logger logger = LoggerFactory.getLogger(BirthdayChecker.class);
	public static final String TOPIC = "C Днем рождения!";
	private boolean check=true;
	
	
	@Override
    public void run() {
        logger.info("BirthdayChecker have started working.");
        while (check) {
        	LocalDate currentDate = new LocalDate();
        	logger.debug("Current date: {}", currentDate);
            int dayOfYear = currentDate.getDayOfYear();
            String text;
        	logger.info("Start sending emails for contacts who was born in day of the year: {}", dayOfYear);
            try {
            	List<Contact> contacts = ContactService.receiveContactsWithBirthday(dayOfYear);
    			for (Contact cont : contacts){
    				TemplateCreator creator=new TemplateCreator();
    				text = creator.formMessage(cont, TemplateType.BIRTHDAY.getTemplate());
    				MailSender.sendMessage(TOPIC, text, cont.getEmail());
    				logger.info("Email was sent for contact: {}", cont);
    			}
			TimeUnit.HOURS.sleep(24);
			} catch (InterruptedException e) {
				logger.error("BirthdayChecker was interrupted: {}", e);
			} catch (ServiceException e) {
				logger.error("Exception during sending birthday emails: {}", e);
			}
        }
        logger.info("BirthdayChecker have finished working.");
    }
	
	 public void setCheck(boolean check) {
	        this.check = check;
	    }
	
}
