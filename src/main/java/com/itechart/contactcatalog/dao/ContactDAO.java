package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Address;
import com.itechart.contactcatalog.subject.Contact;
import com.itechart.contactcatalog.subject.MaritalStatus;
import com.itechart.contactcatalog.subject.Sex;

public class ContactDAO extends AbstractDAO<Contact> {
	private static Logger logger = LoggerFactory.getLogger(ContactDAO.class);
	
	private final static String SQL_SELECT_ROWS_COUNT="SELECT COUNT(*) FROM contact";
	private final static String SQL_SELECT_ALL_CONTACTS_LIMIT="SELECT id, first_name, last_name, midle_name, birth_date, company, country, town, street, house, flat FROM contact LIMIT ?, ?";
	private final static String SQL_SELECT_ALL_CONTACTS="FROM contact";
	private final static String SQL_SELECT_CONTACT_BY_ID="SELECT * FROM contact WHERE id=?";
	private final static String SQL_SELECT_SEX_LIST="SELECT id, title FROM sex";
	private final static String SQL_SELECT_MARITAL_STATUS_LIST="SELECT id, title FROM marital_status";
	private final static String SQL_CONTACT_UPDATE ="UPDATE contact SET first_name=?, last_name=?, midle_name=?, birth_date=?, sex_id=?, marital_status_id=?, citizenship=?, website=?, email=?, image=?, company=?, country=?, town=?, street=?, house=?, flat=?, index_value=? WHERE id=?";
	private final static String SQL_CONTACT_INSERT = "INSERT INTO contact (first_name, last_name, midle_name, birth_date, sex_id, marital_status_id, citizenship, website, email, image, company, country, town, street, house, flat, index_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_CONTACT_DELETE="DELETE FROM contact WHERE id=?";
	private final static String SQL_CONTACT_UPDATE_IMAGE="UPDATE contact SET image=? WHERE id=?";
	private final static String SQL_SELECT_FOR_SEND_EMAILS_TEMPLATE = "SELECT * FROM contact WHERE id IN (%s) AND email IS NOT NULL";
	private final static String SQL_SEARCH_TEMPLATE="FROM contact WHERE %s";
	private final static String SQL_SEARCH_RESULTS="SELECT id, first_name, last_name, midle_name, birth_date, company, country, town, street, house, flat ";
	private final static String SQL_SEARCH_COUNT="SELECT COUNT(*)";
	private final static String SQL_LIMIT = " LIMIT ?, ?";
	private final static String SQL_DATE = "(birth_date BETWEEN ? AND ?)";
	private final static String SQL_AND = " AND ";
	private final static String SQL_FIRST_NAME = "(first_name LIKE ?)";
	private final static String SQL_LAST_NAME = "(last_name LIKE ?)";
	private final static String SQL_MIDDLE_NAME = "(midle_name LIKE ?)";
	private final static String SQL_SEX_ID = "(sex_id=?)";
	private final static String SQL_MARITAL_STATUS_ID = "( marital_status_id=?)";
	private final static String SQL_CITIZENSHIP = "(citizenship LIKE ?)";
	private final static String SQL_COUNTRY = "(country LIKE ?)";
	private final static String SQL_TOWN = "(town LIKE ?)";
	private final static String SQL_STREET = "(street LIKE ?)";
	private final static String SQL_HOUSE = "(house=?)";
	private final static String SQL_FLAT = "(flat=?)";
	private final static String SQL_INDEX = "(index_value=?)";
	
	
	public ContactDAO(Connection connection) {
		super(connection);
	}
	
	public List<Contact> takeContactsForSendingMails(List<Contact> contacts) throws DAOException {
		logger.debug("Start takeContactsForSendingMails method with contacts: ", contacts);
		List<Contact> contactsForSending = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< contacts.size()-1; i++){
			sb.append(contacts.get(i).getId());
			sb.append(",");
		}
		sb.append(contacts.get(contacts.size()-1).getId());
		String statement = String.format(SQL_SELECT_FOR_SEND_EMAILS_TEMPLATE, sb.toString());
		logger.debug("Statement for getting contacts for sending emails: ", statement);
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Contact contact = new Contact();
            	fillContact(contact, rs);
            	contactsForSending.add(contact);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContactsForSendingMails: {} ", e);
            throw new DAOException(e);
        }
        return contactsForSending;
    }
	
	public Contact takeContactById(int id) throws DAOException {
		logger.debug("Start takeContactById method with parameter: id = {}", id);
		Contact contact = new Contact();
		contact.setId(id);
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_CONTACT_BY_ID)) {
        	ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	fillContact(contact, rs);
            }else{
            	logger.error("The contact with this ID doesn't exist.");
            	throw new DAOException("The contact with this ID doesn't exist.");
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContactById: {} ", e);
            throw new DAOException(e);
        }
        return contact;
    }
	
	private void fillContact(Contact contact, ResultSet rs) throws DAOException{
		logger.debug("Start takeContactById method with parameter: contact - ", contact);
		try {
			contact.setId(rs.getInt(1));
			contact.setFirstName(rs.getString(2));
			contact.setLastName(rs.getString(3));
	        contact.setMiddleName(rs.getString(4));
	        contact.setBirthDate(LocalDate.fromDateFields(rs.getDate(5)));
	        Sex sex = new Sex();
	        sex.setId(rs.getString(6).charAt(0));
	        contact.setSex(sex);
	        MaritalStatus status = new MaritalStatus();
	        status.setId(rs.getInt(7));
	        contact.setMaritalStatus(status);
	        contact.setCitizenship(rs.getString(8));
	        contact.setWebsite(rs.getString(9));
	        contact.setEmail(rs.getString(10));
	        contact.setCompany(rs.getString(12));
	        contact.setImage(rs.getString(11));
	        Address address = new Address();
	        address.setCountry(rs.getString(13));
	        address.setTown(rs.getString(14));
	        address.setStreet(rs.getString(15));
	        address.setHouse(rs.getInt(16)!=0?rs.getInt(16):null);
	        address.setFlat(rs.getInt(17)!=0?rs.getInt(17):null);
	        address.setIndexValue(rs.getLong(18)!=0?rs.getLong(18):null);
	        contact.setAddress(address);
		} catch (SQLException e) {
			logger.error("Exception in fillContact: {} ", e);
            throw new DAOException(e);
		}
        
	}
	
	
	public String getSearchStatement(Contact contact, LocalDate more, LocalDate less) throws DAOException{
		logger.debug("Start getSearchStatement method");
		ArrayList<String> templates = new ArrayList<>();
		ArrayList<Object> params = new ArrayList<>();
		String statement;
		boolean isDate = false;
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(contact.getFirstName())){
			templates.add(SQL_FIRST_NAME);
			params.add("%"+contact.getFirstName()+"%");
		}
		if (StringUtils.isNotBlank(contact.getLastName())){
			templates.add(SQL_LAST_NAME);
			params.add("%"+contact.getLastName()+"%");
		}
		if (StringUtils.isNotBlank(contact.getMiddleName())){
			templates.add(SQL_MIDDLE_NAME);
			params.add("%"+contact.getMiddleName()+"%");
		}
		if (contact.getSex()!=null){
			templates.add(SQL_SEX_ID);
			params.add(String.valueOf(contact.getSex().getId()));
		}
		if (contact.getMaritalStatus()!=null){
			templates.add(SQL_MARITAL_STATUS_ID);
			params.add(contact.getMaritalStatus().getId());
		}
		if (StringUtils.isNotBlank(contact.getCitizenship())){
			templates.add(SQL_CITIZENSHIP);
			params.add("%"+contact.getCitizenship()+"%");
		}
		if (StringUtils.isNotBlank(contact.getAddress().getCountry())){
			templates.add(SQL_COUNTRY);
			params.add("%"+contact.getAddress().getCountry()+"%");
		}
		if (StringUtils.isNotBlank(contact.getAddress().getTown())){
			templates.add(SQL_TOWN);
			params.add("%"+contact.getAddress().getTown()+"%");
		}
		if (StringUtils.isNotBlank(contact.getAddress().getStreet())){
			templates.add(SQL_STREET);
			params.add("%"+contact.getAddress().getStreet()+"%");
		}
		if (contact.getAddress().getHouse()!=null){
			templates.add(SQL_HOUSE);
			params.add(contact.getAddress().getHouse());
		}
		if (contact.getAddress().getFlat()!=null){
			templates.add(SQL_FLAT);
			params.add(contact.getAddress().getFlat());
		}
		if (contact.getAddress().getIndexValue()!=null){
			templates.add(SQL_INDEX);
			params.add(contact.getAddress().getIndexValue());
		}
		
		if (templates.size()!=0){
			for (int i=0; i<templates.size()-1; i++){
				sb.append(templates.get(i));
				sb.append(SQL_AND);
			}
			sb.append(templates.get(templates.size()-1));
			if (more!=null && less!=null){
				sb.append(SQL_AND);
				sb.append(SQL_DATE);
				isDate = true;
				
			}
		}else{
			if (more!=null && less!=null){
				sb.append(SQL_DATE);
				isDate = true;
			}
		}
		if (templates.size()==0 && !isDate){
			statement = SQL_SELECT_ALL_CONTACTS;
		}else{
			statement = String.format(SQL_SEARCH_TEMPLATE, sb.toString());
		}
		logger.debug("Template statement for search: {}", statement);
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
        	int index = 1;
        	for (Object param : params){
        		ps.setObject(index, param);
        		logger.debug("Param: {}, {}", index, param);
        		index++;
        	}
        	if (isDate){
        		ps.setDate(index, new Date(more.toDateTimeAtStartOfDay().getMillis()));
        		logger.debug("Param: {}, {}", index, new Date(more.toDateTimeAtStartOfDay().getMillis()));
        		index++;
        		ps.setDate(index, new Date(less.toDateTimeAtStartOfDay().getMillis()));
        		logger.debug("Param: {}, {}", index, new Date(less.toDateTimeAtStartOfDay().getMillis()));
        	}
        	logger.debug("Template statement for search with parameters: {}", StringUtils.substringAfterLast(ps.toString(), ":") );
        	return StringUtils.substringAfterLast(ps.toString(), ":");
        } catch (SQLException e) {
        	logger.error("Exception in getSearchResultStatement: {} ", e);
            throw new DAOException("Database error during getSearchResultStatement.");
        }
	}
	public static String getStatementForSearchCount(String searchStatement){
		logger.debug("Start getSearchCountStatement method");
		StringBuilder sb = new StringBuilder();
		sb.append(SQL_SEARCH_COUNT);
		sb.append(searchStatement);
		logger.debug("Statement for getting count of search results: {}", sb.toString());
        return sb.toString(); 
	}
	
	public static String getStatementForContactsCount(){
        return SQL_SELECT_ROWS_COUNT; 
	}
	
	public static String getStatementForContactsList(){
        return SQL_SELECT_ALL_CONTACTS_LIMIT; 
	}
	
	public static String getStatementForSearchResults(String searchStatement){
		logger.debug("Start getSearchCountStatement method");
		StringBuilder sb = new StringBuilder();
		sb.append(SQL_SEARCH_RESULTS);
		sb.append(searchStatement);
		sb.append(SQL_LIMIT);
		logger.debug("Template statement for getting search results: {}", sb.toString());
        return sb.toString(); 
	}
	
	public ArrayList<Contact> takeContacts(int positionFrom, int count, String statement) throws DAOException {
		logger.debug("Start takeContacts method with parameters: positionFrom - {}, count - {}",positionFrom, count);
		ArrayList<Contact> contacts = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
            ps.setInt(1, positionFrom);
            ps.setInt(2, count);
            logger.debug("Query to db: {}", ps);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(1));
                contact.setFirstName(rs.getString(2));
                contact.setLastName(rs.getString(3));
                contact.setMiddleName(rs.getString(4));
                contact.setBirthDate(LocalDate.fromDateFields(rs.getDate(5)));
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
        	logger.error("Exception in takeContacts: {} ", e);
            throw new DAOException("Database error during contacts taking. Try to make your request later.");
        }
        logger.debug("End takeContacts method");
        return contacts;
    }
	
	public int takeContactsCount(String statement) throws DAOException {
		logger.debug("Start takeContactCount method");
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
        	logger.error("Exception in takeContactCount: {} ", e);
            throw new DAOException("Database error during count of contacts taking.");
        }
    }
	
	public ArrayList<Sex> takeSexList() throws DAOException {
		logger.debug("Start takeSex method");
		ArrayList<Sex> sexList = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_SEX_LIST)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sex sex = new Sex();
                sex.setId(rs.getString(1).charAt(0));
                sex.setTitle(rs.getString(2));
                sexList.add(sex);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeSexList: {} ", e);
            throw new DAOException("Database error during sex list taking. Try to make your request later.");
        }
        return sexList;
    }
	
	public ArrayList<MaritalStatus> takeMaritalStatusList() throws DAOException {
		logger.debug("Start takeMaritalStatusList method");
		ArrayList<MaritalStatus> statusList = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_MARITAL_STATUS_LIST)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MaritalStatus status = new MaritalStatus();
            	status.setId(rs.getInt(1));
            	status.setTitle(rs.getString(2));
                statusList.add(status);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeMaritalStatusList: {} ", e);
            throw new DAOException("Database error during marital status list taking. Try to make your request later.");
        }
        return statusList;
    }
	

	@Override
	public void delete(Contact entity) throws DAOException {
		try(PreparedStatement ps=connection.prepareStatement(SQL_CONTACT_DELETE)) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in delete: {}", e);
            throw new DAOException("Database error during contact deleting.");
        }

	}

	@Override
	public int create(Contact entity) throws DAOException {
		int result = 0;
		try(PreparedStatement ps=connection.prepareStatement(SQL_CONTACT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getLastName());
			ps.setString(3, entity.getMiddleName());
			ps.setDate(4, new Date(entity.getBirthDate().toDateTimeAtStartOfDay().getMillis()));
			ps.setString(5, String.valueOf(entity.getSex().getId()));
			ps.setInt(6, entity.getMaritalStatus().getId());
			ps.setString(7, entity.getCitizenship());
			ps.setString(8, entity.getWebsite());
			ps.setString(9, entity.getEmail());
			ps.setString(10, entity.getImage());
			ps.setString(11, entity.getCompany());
			ps.setString(12, entity.getAddress().getCountry());
			ps.setString(13, entity.getAddress().getTown());
			ps.setString(14, entity.getAddress().getStreet());
			ps.setObject(15, entity.getAddress().getHouse());
			ps.setObject(16, entity.getAddress().getFlat());
			ps.setObject(17, entity.getAddress().getIndexValue());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                result = rs.getInt(1);
            }
            logger.debug("Inserted row: {}", result);
            return result;
        } catch (SQLException e) {
        	logger.error("Exception in create: {}", e);
            throw new DAOException("Database error during contact creating.");
        }

	}
	
	public void updateImage(Contact entity) throws DAOException{
		try(PreparedStatement ps=connection.prepareStatement(SQL_CONTACT_UPDATE_IMAGE)) {
			ps.setString(1, entity.getImage());
			ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in updateImage: {}", e);
            throw new DAOException("Database error during contact image update");
        }
	}

	@Override
	public void update(Contact entity) throws DAOException {
		try(PreparedStatement ps=connection.prepareStatement(SQL_CONTACT_UPDATE)) {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getLastName());
			ps.setString(3, entity.getMiddleName());
			ps.setDate(4, new Date(entity.getBirthDate().toDateTimeAtStartOfDay().getMillis()));
			ps.setString(5, String.valueOf(entity.getSex().getId()));
			ps.setInt(6, entity.getMaritalStatus().getId());
			ps.setString(7, entity.getCitizenship());
			ps.setString(8, entity.getWebsite());
			ps.setString(9, entity.getEmail());
			ps.setString(10, entity.getImage());
			ps.setString(11, entity.getCompany());
			ps.setString(12, entity.getAddress().getCountry());
			ps.setString(13, entity.getAddress().getTown());
			ps.setString(14, entity.getAddress().getStreet());
			ps.setObject(15, entity.getAddress().getHouse());
			ps.setObject(16, entity.getAddress().getFlat());
			ps.setObject(17, entity.getAddress().getIndexValue());
			ps.setInt(18, entity.getId());
            int result  = ps.executeUpdate();
            logger.debug("Row: {}", result);

        } catch (SQLException e) {
        	logger.error("Exception in update: {}", e);
            throw new DAOException("Database error during contact update");
        }

	}
	
	

}
