package com.itechart.contactcatalog.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class ContactListCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactListCommand.class);
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<Contact> contacts = ContactService.receiveContacts();
			request.setAttribute("contacts", contacts);
		} catch (ServiceException e) {
			request.setAttribute("customerror", e.getMessage());
			logger.error("Exception in execute: {}", e);
			return false;
		}
		return true;
		
		
		
		
	}

}
