package com.itechart.contactcatalog.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.template.TemplateType;

public class EmailListCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(EmailListCommand.class);
	
	@Override
	public boolean execute(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info("Start getting emailswith request: {}", ContactServlet.takeRequestInformation(request));
			List<Contact> contacts = DeletingCommand.takeContactListFromRequest(request);
			if (contacts.size()==0){
				request.setAttribute("customerror", "message.search.unchecked");
		        logger.info("No contacts was checked");
			}else{
				List<Contact> contactsWithEmail = ContactService.receiveContactsWithEmail(contacts);
	            if (contactsWithEmail.size()!=0){
	            	TemplateType[] templates = TemplateType.values();
	            	request.setAttribute("templates", templates);
	            	request.setAttribute("contacts", contactsWithEmail);
	            	return true;
	            }else{
	            	request.setAttribute("customerror", "massage.noemail");
			        logger.info("Checked contacts don't have email");
	            }
	        }
			}
	        catch (ServiceException | NumberFormatException e) {
	           request.setAttribute("customerror", e.getMessage());
	           logger.error("Exception in execute: {}", e);
	        }
		return false;
	}

}
