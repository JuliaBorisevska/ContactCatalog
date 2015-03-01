package com.itechart.contactcatalog.logic;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.exception.ConnectionPoolException;
import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.pool.ConnectionPool;
import com.itechart.contactcatalog.subject.Contact;

public class ContactService {

	private static Logger logger = LoggerFactory.getLogger(ContactDAO.class);
	
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
	
}
