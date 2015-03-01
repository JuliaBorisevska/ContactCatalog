package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Address;
import com.itechart.contactcatalog.subject.Contact;

public class ContactDAO extends AbstractDAO<Contact> {
	private static Logger logger = LoggerFactory.getLogger(ContactDAO.class);
	
	private final static String SQL_SELECT_ALL_CONTACTS="SELECT id, first_name, last_name, midle_name, birth_date, company, country, town, street, house, flat FROM contact";
	
	
	public ContactDAO(Connection connection) {
		super(connection);
	}
	
	public ArrayList<Contact> takeContacts() throws DAOException {
		logger.debug("Start takeContact method");
		ArrayList<Contact> contacts = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_ALL_CONTACTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(1));
                contact.setFirstName(rs.getString(2));
                contact.setLastName(rs.getString(3));
                contact.setMiddleName(rs.getString(4));
                contact.setBirthDate(rs.getString(5));
                contact.setCompany(rs.getString(6));
                Address address = new Address();
                address.setCountry(rs.getString(7));
                address.setTown(rs.getString(8));
                address.setStreet(rs.getString(9));
                address.setHouse(rs.getInt(10));
                address.setFlat(rs.getInt(11));
                contact.setAddress(address);
                contacts.add(contact);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContact: {} ", e);
            throw new DAOException("Database error during contacts taking. Try to make your request later.");
        }
        return contacts;
    }
	

	@Override
	public boolean delete(Contact entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Contact entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Contact entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
