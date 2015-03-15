package com.itechart.contactcatalog.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Address;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.MaritalStatus;
import com.itechart.contactcatalog.subject.Sex;

public class SearchContactCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(SearchContactCommand.class);
    
    private static final String TEXT_CONTACT_FIRST_NAME = "firstname";
    private static final String TEXT_CONTACT_LAST_NAME = "lastname";
    private static final String TEXT_CONTACT_MIDDLE_NAME = "middlename";
    private static final String TEXT_CONTACT_YEAR_LESS = "less";
    private static final String TEXT_CONTACT_YEAR_MORE = "more";
    private static final String TEXT_CONTACT_SEX = "sex";
    private static final String TEXT_CONTACT_CITIZENSHIP = "citizenship";
    private static final String TEXT_CONTACT_STATUS = "status";
    private static final String TEXT_CONTACT_COUNTRY = "country";
    private static final String TEXT_CONTACT_TOWN = "town";
    private static final String TEXT_CONTACT_STREET = "street";
    private static final String TEXT_CONTACT_HOUSE = "house";
    private static final String TEXT_CONTACT_FLAT = "flat";
    private static final String TEXT_CONTACT_INDEX = "index";
	
	@Override
	public boolean execute(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			Contact contact = new Contact();
			contact.setFirstName(request.getParameter(TEXT_CONTACT_FIRST_NAME));
    		contact.setLastName(request.getParameter(TEXT_CONTACT_LAST_NAME));
    		contact.setMiddleName(request.getParameter(TEXT_CONTACT_MIDDLE_NAME));
    		LocalDate less = StringUtils.isBlank(request.getParameter(TEXT_CONTACT_YEAR_LESS))?null:LocalDate.parse(request.getParameter(TEXT_CONTACT_YEAR_LESS));
    		LocalDate more = StringUtils.isBlank(request.getParameter(TEXT_CONTACT_YEAR_MORE))?null:LocalDate.parse(request.getParameter(TEXT_CONTACT_YEAR_MORE));
    		contact.setCitizenship(request.getParameter(TEXT_CONTACT_CITIZENSHIP));
    		if (request.getParameter(TEXT_CONTACT_SEX)!=null){
    			Sex sex  = new Sex();
    			sex.setId(request.getParameter(TEXT_CONTACT_SEX).charAt(0));
    			contact.setSex(sex);
    		}
    		if (Integer.valueOf(request.getParameter(TEXT_CONTACT_STATUS))!=0){
    			MaritalStatus status = new MaritalStatus();
    			status.setId(Integer.valueOf(request.getParameter(TEXT_CONTACT_STATUS)));
    			contact.setMaritalStatus(status);
    		}
    		Address address = new Address();
    		address.setCountry(request.getParameter(TEXT_CONTACT_COUNTRY));
    		address.setTown(request.getParameter(TEXT_CONTACT_TOWN));
    		address.setStreet(request.getParameter(TEXT_CONTACT_STREET));
    		address.setHouse(request.getParameter(TEXT_CONTACT_HOUSE).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_HOUSE)));
    		address.setFlat(request.getParameter(TEXT_CONTACT_FLAT).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_FLAT)));
    		address.setIndexValue(request.getParameter(TEXT_CONTACT_INDEX).isEmpty()?null:Long.valueOf(request.getParameter(TEXT_CONTACT_INDEX)));
    		contact.setAddress(address);
    		String templateStatement = ContactService.prepareSearchStatement(contact, more, less);
    		String countStatement = ContactDAO.getStatementForSearchCount(templateStatement);
    		String listStatement = ContactDAO.getStatementForSearchResults(templateStatement);
    		request.getSession().setAttribute("contactListStatement", listStatement);
    		request.getSession().setAttribute("contactCountStatement", countStatement);
    		ContactListCommand command = new ContactListCommand();
    		command.execute(request, response);
		}
		catch (ServiceException | NumberFormatException e) {
	        request.setAttribute("customerror", "message.customerror");
	        logger.error("Exception in execute: {}", e);
	        return false;

	        }
	        return true;
	}

}
