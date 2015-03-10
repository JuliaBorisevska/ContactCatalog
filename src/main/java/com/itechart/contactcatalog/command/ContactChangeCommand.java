package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String TEXT_CONTACT_IMAGE = "image";
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
    private static final String PHOTO_FILE_INPUT = "photo";
	
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
        try {
        	if (ServletFileUpload.isMultipartContent(request)) {
        		logger.debug("Start ContactChangeCommand ");
        		Pattern separator;
        		Contact contact = new Contact();
        		contact.setId(request.getParameter(TEXT_CONTACT_ID).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_ID)));
        		contact.setFirstName(request.getParameter(TEXT_CONTACT_FIRST_NAME));
        		contact.setLastName(request.getParameter(TEXT_CONTACT_LAST_NAME));
        		contact.setMiddleName(request.getParameter(TEXT_CONTACT_MIDDLE_NAME).isEmpty()?null:request.getParameter(TEXT_CONTACT_MIDDLE_NAME));
        		//String date = request.getParameter(TEXT_CONTACT_YEAR);
        		contact.setBirthDate(LocalDate.parse(request.getParameter(TEXT_CONTACT_YEAR)));
        		contact.setCitizenship(request.getParameter(TEXT_CONTACT_CITIZENSHIP).isEmpty()?null:request.getParameter(TEXT_CONTACT_CITIZENSHIP));
        		contact.setEmail(request.getParameter(TEXT_CONTACT_EMAIL).isEmpty()?null:request.getParameter(TEXT_CONTACT_EMAIL));
        		Sex sex  = new Sex();
        		sex.setId(request.getParameter(TEXT_CONTACT_SEX).charAt(0));
        		MaritalStatus status = new MaritalStatus();
        		status.setId(Integer.valueOf(request.getParameter(TEXT_CONTACT_STATUS)));
        		contact.setSex(sex);
        		contact.setMaritalStatus(status);
        		contact.setCompany(request.getParameter(TEXT_CONTACT_JOB).isEmpty()?null:request.getParameter(TEXT_CONTACT_JOB));
        		contact.setWebsite(request.getParameter(TEXT_CONTACT_SITE).isEmpty()?null:request.getParameter(TEXT_CONTACT_SITE));
        		Address address = new Address();
        		address.setCountry(request.getParameter(TEXT_CONTACT_COUNTRY));
        		address.setTown(request.getParameter(TEXT_CONTACT_TOWN));
        		address.setStreet(request.getParameter(TEXT_CONTACT_STREET).isEmpty()?null:request.getParameter(TEXT_CONTACT_STREET));
        		address.setHouse(request.getParameter(TEXT_CONTACT_HOUSE).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_HOUSE)));
        		address.setFlat(request.getParameter(TEXT_CONTACT_FLAT).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_FLAT)));
        		address.setIndexValue(request.getParameter(TEXT_CONTACT_INDEX).isEmpty()?null:Long.valueOf(request.getParameter(TEXT_CONTACT_INDEX)));
        		contact.setAddress(address);
        		String[] fullPhones = request.getParameterValues(TEXT_FULL_PHONE);  //проверка на null 
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
        				//Pattern sep = Pattern.compile("-");
        				//String[] dateParts = sep.split(attachParts[2]);
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
        		
        		ArrayList<Contact> allContacts = ContactService.receiveContacts();
    			request.setAttribute("contacts", allContacts);
        	}
        }
        catch (ServiceException | NumberFormatException e) {
           request.setAttribute("customerror", e.getMessage());
           logger.error("Exception in execute: {}", e);
           return false;

        }
        return true;
	}

}
