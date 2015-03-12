package com.itechart.contactcatalog.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;

public class ContactListCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactListCommand.class);
	private static final String PAGE_NUMBER = "page";
	private static final String INPUT_CHECKED_ROWS_VALUE = "idString";
	private static final String ALL_CONTACTS = "allContacts";
	public static final int PAGE_CONTACTS_COUNT = 2;
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isNotEmpty(request.getParameter(ALL_CONTACTS))){
				request.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
        		request.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
			}
			if (StringUtils.isNotEmpty(request.getParameter(INPUT_CHECKED_ROWS_VALUE))){
				request.setAttribute(INPUT_CHECKED_ROWS_VALUE, request.getParameter(INPUT_CHECKED_ROWS_VALUE));
			}
			logger.debug("Checked rows: {}",request.getAttribute(INPUT_CHECKED_ROWS_VALUE));
			String countStatement = (String) request.getSession().getAttribute("contactCountStatement");
			String listStatement = (String) request.getSession().getAttribute("contactListStatement");
			ArrayList<Contact> contacts;
			int page = request.getParameter(PAGE_NUMBER)!=null?Integer.parseInt(request.getParameter(PAGE_NUMBER)):1;
			int count = ContactService.receiveContactsCount(countStatement);
			int countFrom = page*PAGE_CONTACTS_COUNT-PAGE_CONTACTS_COUNT;
			logger.debug("list of contacts from {} row with count {}", countFrom, PAGE_CONTACTS_COUNT);
			contacts = ContactService.receiveContacts(countFrom,PAGE_CONTACTS_COUNT,listStatement);
			request.setAttribute("contacts", contacts);
			double pageCount = (double)count/PAGE_CONTACTS_COUNT;
			request.setAttribute("count", Math.ceil(pageCount));
			request.setAttribute("pageNumber", page);
		} catch (ServiceException | NumberFormatException e) {
			request.setAttribute("customerror", e.getMessage());
			logger.error("Exception in execute: {}", e);
			return false;
		}
		return true;
		
		
		
		
	}

}
