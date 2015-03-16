package com.itechart.contactcatalog.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.AttachmentDAO;
import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.dao.PhoneDAO;
import com.itechart.contactcatalog.exception.ConnectionPoolException;
import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.exception.UploadException;
import com.itechart.contactcatalog.observing.BirthdayChecker;
import com.itechart.contactcatalog.pool.ConnectionPool;
import com.itechart.contactcatalog.subject.Attachment;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.Phone;
import com.itechart.contactcatalog.template.TemplateCreator;
import com.itechart.contactcatalog.template.TemplateType;


public class ContactService {

	private static Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	
	public static List<Contact> receiveContactsWithEmail(List<Contact> contactList) throws ServiceException {
		logger.debug("Start of receiveContactsWithEmail from contacts: [{}]", contactList);
        Connection conn = null;
        List<Contact> contacts;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);            
            contacts = dao.takeContactsForSendingMails(contactList);
            return contacts;
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in receiveContactsWithEmail: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in receiveContactsWithEmail: {} ", e);
			}
        }
    }
	
	public static List<Contact> receiveContactsWithBirthday(int dayOfYear) throws ServiceException {
		logger.info("Start of receiveContactsWithBirthday: day of the year - {}", dayOfYear);
        Connection conn = null;
        List<Contact> contacts;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);            
            contacts = dao.takeContactsForSendingBirthdayMail(dayOfYear);
            return contacts;
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in receiveContactsWithBirthday: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in receiveContactsWithBirthday: {} ", e);
			}
        }
    }
	
	public static void changeContact(Contact contact, List<FileItem> items) throws ServiceException{
		logger.info("Start of changeContact for contact: [{}]", contact);
		Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            conn.setAutoCommit(false);
            ContactDAO contactDAO = new ContactDAO(conn);
            PhoneDAO phoneDAO = new PhoneDAO(conn);
            AttachmentDAO attachmentDAO = new AttachmentDAO(conn);
            if (contact.getId()==null){
            	int newContactId = contactDAO.create(contact);
            	contact.setId(newContactId);
            	LocalDate currentDate = new LocalDate();
            	logger.debug("Current date: {}", currentDate);
                int dayOfYear = currentDate.getDayOfYear();
                int contactBirthday = contact.getBirthDate().getDayOfYear();
                if (dayOfYear==contactBirthday && StringUtils.isNotBlank(contact.getEmail())){
                	logger.info("Sending birthday message for contact: {}", contact);
                	TemplateCreator creator=new TemplateCreator();
    				String text = creator.formMessage(contact, TemplateType.BIRTHDAY.getTemplate());
    				MailSender.sendMessage(BirthdayChecker.TOPIC, text, contact.getEmail());
                }
            }else{
            	contactDAO.update(contact);
            }
            if (contact.getPhones().size()!=0){
            	for (Phone phone : contact.getPhones()){
            		Integer phoneTypeId = phoneDAO.takePhoneTypeId(phone.getType().getTitle());
            		phone.getType().setId(phoneTypeId);
            		if (phone.getId()==0){
            			int phId=phoneDAO.create(phone);
            			phone.setId(phId);
            		}else{
            			phoneDAO.update(phone);
            		}
            	}
            	ArrayList<Phone> phonesDelete;
            	phonesDelete = phoneDAO.takeContactPhonesForDelete((ArrayList<Phone>)contact.getPhones());
            	for (Phone phone : phonesDelete){
            		phoneDAO.delete(phone);
            	}
            }else{
            	phoneDAO.deleteByContact(contact.getId());
            }
            	
            FileItem photoItem = items.get(0);
            byte[] fileBytes = photoItem.get();
        	if (fileBytes.length!=0){
        		String imageName =photoItem.getName();
    			String ext = FileUploadService.getFileExtention(imageName);
    			contact.setImage(contact.getId()+ext);
    			FileUploadService.uploadFile(photoItem, contact.getImage());
    			contactDAO.updateImage(contact);
        	}
            int index = 2;
            if (contact.getAttachments().size()!=0){
            	for (Attachment attachment : contact.getAttachments()){
            		if (attachment.getId()==0){
            			FileItem item = items.get(index);
            			byte[] bytes = item.get();
                    	if (bytes.length>0){
                    		int attId=attachmentDAO.create(attachment);
                    		attachment.setId(attId);
                    		logger.debug("Field of file field name: {}", item.getFieldName());
                    		String fileName =item.getName();
                    		String ext = FileUploadService.getFileExtention(fileName);
                    		attachment.setPath(attachment.getId()+ext);
                    		FileUploadService.uploadFile(item, attachment.getPath());
                    		attachmentDAO.updatePath(attachment);
                    	}
                		index++;
            		}else{
            			attachmentDAO.update(attachment);
            		}
            	}
            	ArrayList<Attachment> attachsDelete = attachmentDAO.takeAttachmentsForDelete((ArrayList<Attachment>)contact.getAttachments());
            	for (Attachment attach : attachsDelete){
            		attachmentDAO.delete(attach);
            		if (!FileUploadService.deleteUpload(attach.getPath())){
            			logger.error("Can't delete file {} from disk", attach.getPath());
            		}
            	}
            }else{
            	attachmentDAO.deleteByContact(contact.getId());
            }         
            conn.commit();
        } catch (ConnectionPoolException | DAOException | SQLException | UploadException e) {
        	try {
                if(conn!=null) {
                	logger.info("Rollback in changeContact");
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error("Rollback error in changeContact: {}", e1);
            }
        	logger.error("Exception in changeContact: {} ", e);
        	throw new ServiceException(e);
        } finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in changeContact: {} ", e);
			}
        }
	}
	
	
	
	public static List<Contact> receiveContacts(int positionFrom, int count, String statement) throws ServiceException {
		logger.info("Start of receiveContacts with parameters: positionFrom - {}, count - {}", positionFrom, count);
        Connection conn = null;
        List<Contact> contacts;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);            
            contacts = dao.takeContacts(positionFrom, count, statement);
            return contacts;
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in receiveContacts: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in receiveContacts: {} ", e);
			}
        }
    }
	
	public static int receiveContactsCount(String statement) throws ServiceException {
		logger.info("Start of receiveContactsCount with statement: {}", statement);
        Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);
            return dao.takeContactsCount(statement);
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in receiveContactsCount: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in receiveContactsCount: {} ", e);
			}
        }
    }

	public static String prepareSearchStatement(Contact contact, LocalDate more, LocalDate less) throws ServiceException {
		logger.debug("Start of prepareSearchStatement with contact: [{}]", contact);
        Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);
            return dao.getSearchStatement(contact, more, less);
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in findContacts: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in findContacts: {} ", e);
			}
        }
    }
	
	public static Contact receiveContactById(int contactId) throws ServiceException {
		logger.debug("Start of receiveContactById with contactId - {}", contactId);
        Connection conn = null;
        Contact contact;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO contactDao = new ContactDAO(conn);
            PhoneDAO phoneDao = new PhoneDAO(conn);
            AttachmentDAO attachDao = new AttachmentDAO(conn);
            contact = contactDao.takeContactById(contactId);
            ArrayList<Phone> phones = phoneDao.takeContactPhones(contactId);
            ArrayList<Attachment> attachments = attachDao.takeContactAttachments(contactId);
            contact.setPhones(phones);
            contact.setAttachments(attachments);
            return contact;
        } catch (ConnectionPoolException | DAOException e) {
        	logger.error("Exception in receiveContactById: {} ", e);
			throw new ServiceException(e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in receiveContactById: {} ", e);
			}
        }
    }
	
	public static void deleteContacts(List<Contact> contacts) throws ServiceException {
        Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            conn.setAutoCommit(false);
            ContactDAO contactDao = new ContactDAO(conn);
            for (Contact contact : contacts){
            	contactDao.delete(contact);
            }
            conn.commit();
        } catch (ConnectionPoolException | DAOException | SQLException e) {
            try {
                if(conn!=null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error("Rollback error in deleteContacts: {}", e1);
            }
            logger.error("Exception in deleteContacts: {} ", e);
        	throw new ServiceException(e);
        } finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in deleteContacts: {} ", e);
			}
        }
    }
	

	
	
	
}
