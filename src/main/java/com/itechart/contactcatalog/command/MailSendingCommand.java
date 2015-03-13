package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.template.TemplateCreator;
import com.itechart.contactcatalog.template.TemplateType;

public class MailSendingCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(MailSendingCommand.class);
	private static final String TEXT_CONTACT_ID = "idContact";
    private static final String TEXT_TOPIC = "topic";
    private static final String TEXT_TEMPLATE = "template";
    private static final String TEXT_LETTER = "letter";
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		try{
			String[] ids = request.getParameterValues(TEXT_CONTACT_ID);
			List<Contact> contacts = new ArrayList<>();
			for (String id : ids){
				Contact contact =ContactService.receiveContactById(Integer.valueOf(id));
				contacts.add(contact);
			}
			String topic = request.getParameter(TEXT_TOPIC);
			String template = request.getParameter(TEXT_TEMPLATE);
			logger.debug("template {}", template);
			String text = request.getParameter(TEXT_LETTER);
			logger.debug(text);
			for (Contact cont : contacts){
				if (!TEXT_LETTER.equals(template)){
					TemplateCreator creator=new TemplateCreator();
					text = creator.formMessage(cont, template);
				}
				ContactService.sendMessage(topic, text, cont.getEmail());
			}
			request.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
    		request.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
    		ContactListCommand command = new ContactListCommand();
    		return command.execute(request, response);
		}catch(ServiceException | NumberFormatException e) {
	           request.setAttribute("customerror", e.getMessage());
	           logger.error("Exception in execute: {}", e);
	        }
		
		return false;
	}

}
