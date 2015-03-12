package com.itechart.contactcatalog.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class ContactEditingCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactEditingCommand.class);
	
	private static final String CONTACT_ID_PARAMETER = "id";
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		
        try {
        	int id = Integer.parseInt(request.getParameter(CONTACT_ID_PARAMETER));
			Contact contact = ContactService.receiveContactById(id);
			request.setAttribute("contact", contact);
		} catch (ServiceException e) {
			request.setAttribute("customerror", e.getMessage());
			logger.error("Exception in execute: {}", e);
			return false;
		}
        
		return true;
	}

}
