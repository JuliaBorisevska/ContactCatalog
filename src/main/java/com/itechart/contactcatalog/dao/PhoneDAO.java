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
	private final static String SQL_SELECT_TYPE_ID="SELECT id FROM phone_type WHERE title=?";
	private final static String SQL_SELECT_CONTACT_PHONES="SELECT phone.*, phone_type.title FROM phone JOIN contact ON phone.contact_id=contact.id JOIN phone_type ON phone.phone_type_id=phone_type.id WHERE contact.id=?";
	private final static String SQL_PHONE_UPDATE = "UPDATE phone SET country_code=?, operator_code=?, basic_number=?, phone_type_id=?, user_comment=? WHERE id=?";
	private final static String SQL_PHONE_DELETE="DELETE FROM phone WHERE id=?";
	private final static String SQL_PHONE_INSERT = "INSERT INTO phone (country_code, operator_code, basic_number, phone_type_id, user_comment, contact_id) VALUES (?, ?, ?, ?, ?, ?)";
	private final static String SQL_SELECT_PHONE_FOR_DELETE_TEMPLATE = "SELECT id FROM phone WHERE id NOT IN (%s) AND contact_id=?";
	private final static String SQL_DELETE_ALL_CONTACT_PHONES = "DELETE FROM phone WHERE contact_id=?";
	
	public PhoneDAO(Connection connection) {
		super(connection);
	}

	public ArrayList<Phone> takeContactPhonesForDelete(ArrayList<Phone> ph) throws DAOException {
		logger.info("Start takeContactPhonesForDelete method");
		ArrayList<Phone> phones = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< ph.size()-1; i++){
			sb.append(ph.get(i).getId());
			sb.append(",");
		}
		sb.append(ph.get(ph.size()-1).getId());
		String statement = String.format(SQL_SELECT_PHONE_FOR_DELETE_TEMPLATE, sb.toString());
		logger.debug("Statement for phones deleting: ", statement);
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
        	ps.setInt(1, ph.get(0).getContact().getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Phone phone = new Phone();
            	phone.setId(rs.getInt(1));
                phones.add(phone);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContactPhonesForDelete: {} ", e);
            throw new DAOException(e);
        }
        return phones;
    }
	
	public ArrayList<Phone> takeContactPhones(int contactId) throws DAOException {
		logger.info("Start takeContactPhones method");
		ArrayList<Phone> phones = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_CONTACT_PHONES)) {
        	ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Phone phone = new Phone();
            	phone.setId(rs.getInt(1));
                phone.setCountryCode(rs.getInt(2));
                phone.setOperatorCode(rs.getInt(3));
                phone.setBasicNumber(rs.getLong(4));
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
		logger.info("Start takePhoneTypes method");
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
	
	public Integer takePhoneTypeId(String title) throws DAOException {
		logger.info("Start takePhoneTypeId method");
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_TYPE_ID)) {
        	ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return Integer.valueOf(rs.getInt(1));
        } catch (SQLException e) {
        	logger.error("Exception in takePhoneTypes: {} ", e);
            throw new DAOException("Database error during phone type id taking.");
        }
    }
	
	public void deleteByContact(int contactId) throws DAOException {
		logger.info("Start deleteByContact method with contact id: {}", contactId);
		try(PreparedStatement ps=connection.prepareStatement(SQL_DELETE_ALL_CONTACT_PHONES)) {
            ps.setInt(1, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in delete: {}", e);
            throw new DAOException("Database error during phone deleting.");
        }
	}
	
	
	@Override
	public void delete(Phone entity) throws DAOException {
		logger.info("Start delete method");
		try(PreparedStatement ps=connection.prepareStatement(SQL_PHONE_DELETE)) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in delete: {}", e);
            throw new DAOException("Database error during phone deleting.");
        }

	}

	@Override
	public int create(Phone entity) throws DAOException {
		logger.info("Start create method");
		int result = 0;
		try(PreparedStatement ps=connection.prepareStatement(SQL_PHONE_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, entity.getCountryCode());
			ps.setInt(2, entity.getOperatorCode());
			ps.setLong(3, entity.getBasicNumber());
			ps.setInt(4, entity.getType().getId());
			ps.setString(5, entity.getUserComment());
			ps.setInt(6, entity.getContact().getId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                result = rs.getInt(1);
            }
            logger.debug("Inserted row: {}", result);
            return result;
        } catch (SQLException e) {
        	logger.error("Exception in create: {}", e);
            throw new DAOException("Database error during phone creating.");
        }

	}

	@Override
	public void update(Phone entity) throws DAOException {
		logger.info("Start update method");
		try(PreparedStatement ps=connection.prepareStatement(SQL_PHONE_UPDATE)) {
			ps.setInt(1, entity.getCountryCode());
			ps.setInt(2, entity.getOperatorCode());
			ps.setLong(3, entity.getBasicNumber());
			ps.setInt(4, entity.getType().getId());
			ps.setString(5, entity.getUserComment());
			ps.setInt(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in update: {}", e);
            throw new DAOException("Database error during phone update");
        }
	}

}
