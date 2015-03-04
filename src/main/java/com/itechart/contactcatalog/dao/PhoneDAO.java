package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Phone;
import com.itechart.contactcatalog.subject.PhoneType;

public class PhoneDAO extends AbstractDAO<Phone> {
	private static Logger logger = LoggerFactory.getLogger(PhoneDAO.class);
	
	private final static String SQL_SELECT_PHONE_TYPES="SELECT id, title FROM phone_type";
	private final static String SQL_SELECT_CONTACT_PHONES="SELECT phone.*, phone_type.title FROM phone JOIN contact ON phone.contact_id=contact.id JOIN phone_type ON phone.phone_type_id=phone_type.id WHERE contact.id=?";

	public PhoneDAO(Connection connection) {
		super(connection);
	}

	public ArrayList<Phone> takeContactPhones(int contactId) throws DAOException {
		logger.debug("Start takeContactPhones method");
		ArrayList<Phone> phones = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_CONTACT_PHONES)) {
        	ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Phone phone = new Phone();
            	phone.setId(rs.getInt(1));
                phone.setCountryCode(rs.getInt(2));
                phone.setOperatorCode(rs.getInt(3));
                phone.setBasicNumber(rs.getInt(4));
                PhoneType type = new PhoneType();
                type.setId(rs.getInt(5));
                type.setTitle(rs.getString(8));;
                phone.setType(type);
                phone.setUserComment(rs.getString(6));
                phones.add(phone);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContactPhones: {} ", e);
            throw new DAOException("Database error during takeContactPhones. Try to make your request later.");
        }
        return phones;
    }
	
	public ArrayList<PhoneType> takePhoneTypes() throws DAOException {
		logger.debug("Start takePhoneTypes method");
		ArrayList<PhoneType> phoneTypes = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_PHONE_TYPES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	PhoneType type = new PhoneType();
            	type.setId(rs.getInt(1));
            	type.setTitle(rs.getString(2));
            	phoneTypes.add(type);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takePhoneTypes: {} ", e);
            throw new DAOException("Database error during phone types taking. Try to make your request later.");
        }
        return phoneTypes;
    }
	
	@Override
	public boolean delete(Phone entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Phone entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Phone entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
