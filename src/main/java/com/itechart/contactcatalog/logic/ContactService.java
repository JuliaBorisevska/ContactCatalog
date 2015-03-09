package com.itechart.contactcatalog.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.AttachmentDAO;
import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.dao.PhoneDAO;
import com.itechart.contactcatalog.exception.ConnectionPoolException;
import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.pool.ConnectionPool;
import com.itechart.contactcatalog.subject.Attachment;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.Phone;

public class ContactService {

	private static Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	
	public static void changeContact(Contact contact) throws ServiceException{
		logger.debug("Start of changeContact");
		Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            conn.setAutoCommit(false);
            ContactDAO contactDAO = new ContactDAO(conn);
            PhoneDAO phoneDAO = new PhoneDAO(conn);
            AttachmentDAO attachmentDAO = new AttachmentDAO(conn);
            
            
            
            conn.commit();
        } catch (ConnectionPoolException | DAOException | SQLException e) {
        	try {
                if(conn!=null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error("Rollback error in changeContact: {}", e1);
            }
        	throw new ServiceException(e);
        } finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in changeContact: {} ", e);
			}
        }
	}
	
	
	
	public static ArrayList<Contact> receiveContacts() throws ServiceException {
		logger.debug("Start of receiveContacts");
        Connection conn = null;
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        try {
            conn = ConnectionPool.getInstance().getConnection();
            ContactDAO dao = new ContactDAO(conn);
            contacts = dao.takeContacts();
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
	
	public static Contact receiveContactById(int contactId) throws ServiceException {
		logger.debug("Start of receiveContactById");
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
	
	
	
}
