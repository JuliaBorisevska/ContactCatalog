package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.logic.ContactService;
import com.itechart.contactcatalog.subject.Address;
import com.itechart.contactcatalog.subject.Attachment;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.MaritalStatus;
import com.itechart.contactcatalog.subject.Phone;
import com.itechart.contactcatalog.subject.PhoneType;
import com.itechart.contactcatalog.subject.Sex;
import com.itechart.contactcatalog.wrapper.FileUploadWrapper;

public class ContactChangeCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactChangeCommand.class);
    
    private static final String TEXT_CONTACT_ID = "contactId";
    private static final String TEXT_CONTACT_FIRST_NAME = "firstname";
    private static final String TEXT_CONTACT_LAST_NAME = "lastname";
    private static final String TEXT_CONTACT_MIDDLE_NAME = "middlename";
    private static final String TEXT_CONTACT_YEAR = "year";
    private static final String TEXT_CONTACT_SEX = "sex";
    private static final String TEXT_CONTACT_CITIZENSHIP = "citizenship";
    private static final String TEXT_CONTACT_STATUS = "status";
    private static final String TEXT_CONTACT_SITE = "site";
    private static final String TEXT_CONTACT_EMAIL = "email";
    private static final String TEXT_CONTACT_JOB = "job";
    private static final String TEXT_CONTACT_COUNTRY = "country";
    private static final String TEXT_CONTACT_TOWN = "town";
    private static final String TEXT_CONTACT_STREET = "street";
    private static final String TEXT_CONTACT_HOUSE = "house";
    private static final String TEXT_CONTACT_FLAT = "flat";
    private static final String TEXT_CONTACT_INDEX = "index";
    private static final String TEXT_FULL_PHONE = "phone";
    private static final String TEXT_FULL_ATTACHMENT = "attachment";
	
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
        try {
        	if (ServletFileUpload.isMultipartContent(request)) {
        		logger.debug("Start ContactChangeCommand ");
        		Pattern separator;
        		Contact contact = new Contact();
        		String id = request.getParameter(TEXT_CONTACT_ID);
        		String firstName = request.getParameter(TEXT_CONTACT_FIRST_NAME);
        		String lastName = request.getParameter(TEXT_CONTACT_LAST_NAME);
        		String middleName = request.getParameter(TEXT_CONTACT_MIDDLE_NAME);
        		String birthDate = request.getParameter(TEXT_CONTACT_YEAR);
        		String citizenship = request.getParameter(TEXT_CONTACT_CITIZENSHIP);
        		String email = request.getParameter(TEXT_CONTACT_EMAIL);
        		String company = request.getParameter(TEXT_CONTACT_JOB);
        		String website = request.getParameter(TEXT_CONTACT_SITE);
        		String country = request.getParameter(TEXT_CONTACT_COUNTRY);
        		String town = request.getParameter(TEXT_CONTACT_TOWN);
        		String street = request.getParameter(TEXT_CONTACT_STREET);
        		String house = request.getParameter(TEXT_CONTACT_HOUSE);
        		String flat =request.getParameter(TEXT_CONTACT_FLAT);
        		String index = request.getParameter(TEXT_CONTACT_INDEX);
        		contact.setId(StringUtils.isBlank(id)?null:Integer.valueOf(id));
        		contact.setFirstName(firstName);
        		contact.setLastName(lastName);
        		contact.setMiddleName(StringUtils.isBlank(middleName)?null:middleName);
        		//String date = request.getParameter(TEXT_CONTACT_YEAR);
        		contact.setBirthDate(LocalDate.parse(birthDate));
        		contact.setCitizenship(StringUtils.isBlank(citizenship)?null:citizenship);
        		contact.setEmail(StringUtils.isBlank(email)?null:email);
        		Sex sex  = new Sex();
        		sex.setId(request.getParameter(TEXT_CONTACT_SEX).charAt(0));
        		MaritalStatus status = new MaritalStatus();
        		status.setId(Integer.valueOf(request.getParameter(TEXT_CONTACT_STATUS)));
        		contact.setSex(sex);
        		contact.setMaritalStatus(status);
        		contact.setCompany(StringUtils.isBlank(company)?null:company);
        		contact.setWebsite(StringUtils.isBlank(website)?null:website);
        		Address address = new Address();
        		address.setCountry(country);
        		address.setTown(town);
        		address.setStreet(StringUtils.isBlank(street)?null:street);
        		address.setHouse(StringUtils.isBlank(house)?null:Integer.valueOf(house));
        		address.setFlat(StringUtils.isBlank(flat)?null:Integer.valueOf(flat));
        		address.setIndexValue(StringUtils.isBlank(index)?null:Long.valueOf(index));
        		contact.setAddress(address);
        		String[] fullPhones = request.getParameterValues(TEXT_FULL_PHONE);
        		String[] fullAttachments = request.getParameterValues(TEXT_FULL_ATTACHMENT);
        		separator = Pattern.compile(":");
        		ArrayList<Phone> phones = new ArrayList<Phone>(); 
            	if (fullPhones!=null){
        			for (String ph : fullPhones){
        				logger.debug(ph);
        				String [] parts = separator.split(ph);
        				Phone phone = new Phone();
        				phone.setContact(contact);
        				phone.setId(Integer.valueOf(parts[0]));
        				phone.setCountryCode(Integer.valueOf(parts[1]));
        				phone.setOperatorCode(Integer.valueOf(parts[2]));
        				phone.setBasicNumber(Long.valueOf(parts[3]));
        				PhoneType type = new PhoneType();
        				type.setTitle(parts[4]);
        				phone.setType(type);
        				phone.setUserComment(parts.length==5?null:parts[5]);
        				phones.add(phone);
        			}
        		}
        		contact.setPhones(phones);
        		separator = Pattern.compile("#");
        		ArrayList<Attachment> attachs = new ArrayList<>(); 
            	if (fullAttachments!=null){
        			for (String att : fullAttachments){
        				logger.debug(att);
        				String [] attachParts = separator.split(att);
        				Attachment attachment = new Attachment();
        				attachment.setId(Integer.valueOf(attachParts[0]));
        				attachment.setTitle(attachParts[1]);
        				attachment.setContact(contact);
        				DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm-ss");
        				LocalDateTime dateTime = LocalDateTime.parse(attachParts[2], fmt);
        				attachment.setUploads(dateTime);
        				attachment.setUserComment(attachParts.length==3?null:attachParts[3]);
        				attachs.add(attachment);
        			}
        		}
        			contact.setAttachments(attachs);

        		FileUploadWrapper req = (FileUploadWrapper) request;
        		List<FileItem> items = req.getFileItems();
        		ContactService.changeContact(contact, items);
        		request.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
        		request.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
        		ContactListCommand command = new ContactListCommand();
        		return command.execute(request, response);
        	}else{
        		request.setAttribute("customerror", "Что-то пошло не так...");
                logger.error("Request doesn't have multipart content");
        	}
        }
        catch (ServiceException | NumberFormatException e) {
           request.setAttribute("customerror", e.getMessage());
           logger.error("Exception in execute: {}", e);
        }
        return false; 
	}

}
