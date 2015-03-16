package com.itechart.contactcatalog.command;

import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.logic.DataValidator;
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
			logger.info("Start SearchContactCommand with request: {}", ContactServlet.takeRequestInformation(request));
			String firstName = request.getParameter(TEXT_CONTACT_FIRST_NAME);
    		String lastName = request.getParameter(TEXT_CONTACT_LAST_NAME);
    		String middleName = request.getParameter(TEXT_CONTACT_MIDDLE_NAME);
    		String dateFrom = request.getParameter(TEXT_CONTACT_YEAR_MORE);
    		String dateTo = request.getParameter(TEXT_CONTACT_YEAR_LESS);
    		String citizenship = request.getParameter(TEXT_CONTACT_CITIZENSHIP);
    		String country = request.getParameter(TEXT_CONTACT_COUNTRY);
    		String town = request.getParameter(TEXT_CONTACT_TOWN);
    		String street = request.getParameter(TEXT_CONTACT_STREET);
    		String house = request.getParameter(TEXT_CONTACT_HOUSE);
    		String flat =request.getParameter(TEXT_CONTACT_FLAT);
    		String index = request.getParameter(TEXT_CONTACT_INDEX);
			if (!DataValidator.validateSearchDate(firstName, lastName, middleName, dateFrom, dateTo,
					citizenship, country, town, street, house, flat, index)){
    			request.setAttribute("customerror", "message.data.invalid");
                logger.error("Input data are invalid");
                return false;
    		}
			Contact contact = new Contact();
			contact.setFirstName(firstName);
    		contact.setLastName(lastName);
    		contact.setMiddleName(middleName);
    		LocalDate less = StringUtils.isBlank(dateTo)?null:LocalDate.parse(dateTo);
    		LocalDate more = StringUtils.isBlank(dateFrom)?null:LocalDate.parse(dateFrom);
    		contact.setCitizenship(citizenship);
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
    		address.setCountry(country);
    		address.setTown(town);
    		address.setStreet(street);
    		address.setHouse(StringUtils.isBlank(house)?null:Integer.valueOf(house));
    		address.setFlat(StringUtils.isBlank(flat)?null:Integer.valueOf(flat));
    		address.setIndexValue(StringUtils.isBlank(index)?null:Long.valueOf(index));
    		contact.setAddress(address);
    		String templateStatement = ContactService.prepareSearchStatement(contact, more, less);
    		String countStatement = ContactDAO.getStatementForSearchCount(templateStatement);
    		String listStatement = ContactDAO.getStatementForSearchResults(templateStatement);
    		request.getSession().setAttribute("contactListStatement", listStatement);
    		request.getSession().setAttribute("contactCountStatement", countStatement);
    		ContactListCommand command = new ContactListCommand();
    		command.execute(request, response);
		}
		catch (ServiceException | NumberFormatException | DateTimeParseException e) {
	        request.setAttribute("customerror", "message.customerror");
	        logger.error("Exception in execute: {}", e);
	        return false;
	    }
	    return true;
	}

}
