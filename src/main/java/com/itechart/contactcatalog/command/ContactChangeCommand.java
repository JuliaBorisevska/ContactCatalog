package com.itechart.contactcatalog.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.subject.Address;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.MaritalStatus;
import com.itechart.contactcatalog.subject.Phone;
import com.itechart.contactcatalog.subject.PhoneType;
import com.itechart.contactcatalog.subject.Sex;

public class ContactChangeCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactChangeCommand.class);
	private static final String BUTTON_SAVE_CONTACT = "saveContact";
    private static final String BUTTON_CANCEL_CONTACT = "cancelContact";
    private static final String BUTTON_SAVE_IMAGE = "saveImage";
    private static final String BUTTON_CANCEL_IMAGE = "cancelImage";
    private static final String BUTTON_SAVE_PHONE = "savePhone";
    private static final String BUTTON_CANCEL_PHONE = "cancelPhone";
    private static final String BUTTON_SAVE_ATTACHMENT = "saveAttach";
    private static final String BUTTON_CANCEL_ATTACHMENT = "cancelAttach";
    
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
    private static final String TEXT_PHONE_EDIT = "edit";
    private static final String TEXT_PHONE_ID = "phoneId";
    private static final String TEXT_PHONE_OPERATOR_CODE = "operatorCode";
    private static final String TEXT_PHONE_COUNTRY_CODE = "countryCode";
    private static final String TEXT_PHONE_NUMBER = "number";
    private static final String TEXT_PHONE_TYPE = "phoneType";
    private static final String TEXT_PHONE_COMMENT = "phoneComment";
    private static final String TEXT_ATTACHMENT_ID = "attachId";
    private static final String TEXT_ATTACHMENT_NAME = "attachName";
    private static final String TEXT_ATTACHMENT_COMMENT = "attachComment";
	
	
	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Start ContactChangeCommand ");
		Contact contact = new Contact();
		contact.setFirstName(request.getParameter(TEXT_CONTACT_FIRST_NAME));
		contact.setLastName(request.getParameter(TEXT_CONTACT_LAST_NAME));
		contact.setMiddleName(request.getParameter(TEXT_CONTACT_MIDDLE_NAME));
		contact.setBirthDate(request.getParameter(TEXT_CONTACT_YEAR));
		contact.setCitizenship(request.getParameter(TEXT_CONTACT_CITIZENSHIP));
		contact.setEmail(request.getParameter(TEXT_CONTACT_EMAIL));
		Sex sex  = new Sex();
		sex.setId(request.getParameter(TEXT_CONTACT_SEX).charAt(0));
		MaritalStatus status = new MaritalStatus();
		status.setId(Integer.valueOf(request.getParameter(TEXT_CONTACT_STATUS)));
		contact.setSex(sex);
		contact.setMaritalStatus(status);
		contact.setCompany(request.getParameter(TEXT_CONTACT_JOB));
		contact.setWebsite(request.getParameter(TEXT_CONTACT_SITE));
		Address address = new Address();
		address.setCountry(request.getParameter(TEXT_CONTACT_COUNTRY));
		address.setTown(request.getParameter(TEXT_CONTACT_TOWN));
		address.setStreet(request.getParameter(TEXT_CONTACT_STREET));
		address.setHouse(request.getParameter(TEXT_CONTACT_HOUSE).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_HOUSE)));
		address.setFlat(request.getParameter(TEXT_CONTACT_FLAT).isEmpty()?null:Integer.valueOf(request.getParameter(TEXT_CONTACT_FLAT)));
		address.setIndexValue(request.getParameter(TEXT_CONTACT_INDEX).isEmpty()?null:Long.valueOf(request.getParameter(TEXT_CONTACT_INDEX)));
		String[] fullPhones = request.getParameterValues(TEXT_FULL_PHONE);
		Pattern separator = Pattern.compile(":");
		ArrayList<Phone> phones = new ArrayList<Phone>(); 
		for (String ph : fullPhones){
			logger.debug(ph);
			String [] parts = separator.split(ph);
			Phone phone = new Phone();
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
		contact.setPhones(phones);
		Enumeration<String> params=request.getParameterNames();
        try {
        	while (params.hasMoreElements()){
                String paramName=params.nextElement();
                switch (paramName){
                	case BUTTON_SAVE_CONTACT:
                	{
                		logger.debug("Save contact operation ");
                		break;
                	}
                	case BUTTON_SAVE_IMAGE:
                	{
                		logger.debug("Save image operation ");
                		break;
                	}
                	case BUTTON_SAVE_PHONE:
                	{
                		logger.debug("Save phone operation ");
                		if (Integer.valueOf(request.getParameter(TEXT_PHONE_EDIT))==0){
                			Phone newPhone = new Phone();
                			newPhone.setId(0);
                			newPhone.setCountryCode(Integer.valueOf(request.getParameter(TEXT_PHONE_COUNTRY_CODE)));
                			newPhone.setOperatorCode(Integer.valueOf(request.getParameter(TEXT_PHONE_OPERATOR_CODE)));
                			newPhone.setBasicNumber(Long.valueOf(request.getParameter(TEXT_PHONE_NUMBER)));
                			PhoneType type = new PhoneType();
                			type.setTitle(request.getParameter(TEXT_PHONE_TYPE));
                			newPhone.setType(type);
                			newPhone.setUserComment(request.getParameter(TEXT_PHONE_COMMENT));
                			contact.getPhones().add(newPhone);
                		}
                		break;
                	}
                	case BUTTON_SAVE_ATTACHMENT:
                	{
                		logger.debug("Save attachment operation ");
                		break;
                	}
                	case BUTTON_CANCEL_CONTACT:
                	{
                		
                		break;
                	}
                	case BUTTON_CANCEL_IMAGE:
                	{
                		
                		break;
                	}
                	case BUTTON_CANCEL_PHONE:
                	{
                		
                		break;
                	}
                	case BUTTON_CANCEL_ATTACHMENT:
                	{
                		
                		break;
                	}
                }
        	}
        }
        //catch (ServiceException e) {
        //    requestContent.getRequestAttributes().put("message",e.getMessage());

        //}
        catch (NumberFormatException e) {
            //request.setAttribute("message",e.getMessage());
 
        }
        request.setAttribute("contact", contact);
		return true;
	}

}
