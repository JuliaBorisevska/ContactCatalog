package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class DeletingCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(DeletingCommand.class);

	private static final String INPUT_CHECKED_ROWS = "idString";
	
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("Start deleting contacts with request: {}", ContactServlet.takeRequestInformation(request));
			List<Contact> contacts = takeContactListFromRequest(request);
		if (contacts.size()==0){
			request.setAttribute("customerror", "message.search.unchecked");
	        logger.info("No contacts was checked");
	        return false;
		}else{
            ContactService.deleteContacts(contacts);
        }
        request.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
		request.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
		ContactListCommand command = new ContactListCommand();
		return command.execute(request, response);
		}
        catch (ServiceException | NumberFormatException e) {
           request.setAttribute("customerror", "message.customerror");
           logger.error("Exception in execute: {}", e);
           return false;
        }
        
	}
	
	public static List<Contact> takeContactListFromRequest(HttpServletRequest request){
		String idValues = request.getParameter(INPUT_CHECKED_ROWS);
		List<Contact> contacts = new ArrayList<>();
		if (StringUtils.isNotEmpty(idValues)){
			Pattern separator = Pattern.compile(",");
			String [] idArray = separator.split(idValues);
			for (int i=0; i<idArray.length ;i++){
				logger.debug("Array element {} of checked contact's ids: {}",i, idArray[i]);
				if(StringUtils.isNotEmpty(idArray[i])){
					Contact contact = new Contact();
					contact.setId(Integer.valueOf(idArray[i]));
					contacts.add(contact);
				}
			}
		}
		return contacts;
	}

}
