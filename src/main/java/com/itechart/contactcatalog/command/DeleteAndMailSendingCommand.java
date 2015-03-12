package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class DeleteAndMailSendingCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(DeleteAndMailSendingCommand.class);
	private static final String BUTTON_DELETE_CONTACTS = "delete";
	private static final String BUTTON_SEND_MAIL = "send";
	private static final String INPUT_CHECKED_ROWS = "checkedRows";
	
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		try {
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
			
		}else{
			request.setAttribute("customerror", "No contacts was checked!");
	        logger.debug("No contacts was checked");
	        return false;
		}
		logger.debug("Checked rows: {}",idValues);
		Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String paramName=params.nextElement();
            switch (paramName){
            	case BUTTON_DELETE_CONTACTS:
            	{
            		logger.debug("Start deleting contacts");
            		ContactService.deleteContacts(contacts);
            		break;
            	}
            	case BUTTON_SEND_MAIL:
            	{
            		logger.debug("Start sending mails");
            		break;
            	}
            
            }
        }
        request.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
		request.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
		ContactListCommand command = new ContactListCommand();
		command.execute(request, response);
		}
        catch (ServiceException | NumberFormatException e) {
           request.setAttribute("customerror", e.getMessage());
           logger.error("Exception in execute: {}", e);
           return false;
        } 
        return true;
	}

}
