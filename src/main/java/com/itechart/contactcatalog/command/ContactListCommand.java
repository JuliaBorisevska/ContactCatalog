package com.itechart.contactcatalog.command;

import java.util.ArrayList;

import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class ContactListCommand implements ActionCommand {

	@Override
	public int execute(SessionRequestContent requestContent) {
		try {
			ArrayList<Contact> contacts = ContactService.receiveContacts();
			requestContent.getRequestAttributes().put("contacts", contacts);
		} catch (ServiceException e) {
			requestContent.getRequestAttributes().put("customerror", e.getMessage());
			// log + записать e в requestContent
			return 1;
		}
		return 0;
		
		
		
		
	}

}
