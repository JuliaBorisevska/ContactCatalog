package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Attachment;



public class AttachmentDAO extends AbstractDAO<Attachment> {
	private static Logger logger = LoggerFactory.getLogger(AttachmentDAO.class);
	
	private final static String SQL_SELECT_CONTACT_ATTACHMENTS="SELECT attachment.* FROM attachment JOIN contact ON attachment.contact_id=contact.id WHERE contact.id=?";
	private final static String SQL_ATTACHMENT_UPDATE="UPDATE attachment SET title=?, user_comment=? WHERE id=?"; 
	private final static String SQL_ATTACHMENT_UPDATE_PATH="UPDATE attachment SET path=? WHERE id=?"; 
	private final static String SQL_ATTACHMENT_DELETE="DELETE FROM attachment WHERE id=?";
	private final static String SQL_ATTACHMENT_INSERT = "INSERT INTO attachment (title, path, upload_date, user_comment, contact_id) VALUES (?, ?, ?, ?, ?)";
	private final static String SQL_SELECT_ATTACHMENT_FOR_DELETE_TEMPLATE = "SELECT id, path FROM attachment WHERE id NOT IN (%s) AND contact_id=?";
	private final static String SQL_DELETE_ALL_CONTACT_ATTACHMENTS = "DELETE FROM attachment WHERE contact_id=?";
	
	public AttachmentDAO(Connection connection) {
		super(connection);
	}

	public ArrayList<Attachment> takeAttachmentsForDelete(ArrayList<Attachment> attachs) throws DAOException {
		logger.info("Start takeContactPhonesForDelete method");
		ArrayList<Attachment> attachments = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< attachs.size()-1; i++){
			sb.append(attachs.get(i).getId());
			sb.append(",");
		}
		sb.append(attachs.get(attachs.size()-1).getId());
		String statement = String.format(SQL_SELECT_ATTACHMENT_FOR_DELETE_TEMPLATE, sb.toString());
		logger.debug(statement);
        try(PreparedStatement ps=connection.prepareStatement(statement)) {
        	ps.setInt(1, attachs.get(0).getContact().getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Attachment attachment = new Attachment();
            	attachment.setId(rs.getInt(1));
            	attachment.setPath(rs.getString(2));
            	attachments.add(attachment);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeAttachmentsForDelete: {} ", e);
            throw new DAOException("Database error during takeAttachmentsForDelete.");
        }
        return attachments;
    }
	
	public ArrayList<Attachment> takeContactAttachments(int contactId) throws DAOException {
		logger.info("Start takeContactAttachments method with id of the contact: {}", contactId);
		ArrayList<Attachment> attachments = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_CONTACT_ATTACHMENTS)) {
        	ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Attachment attach = new Attachment();
            	logger.debug("DateTime: {}", LocalDateTime.fromDateFields((rs.getTimestamp(4))));
            	attach.setId(rs.getInt(1));
                attach.setTitle(rs.getString(2));
                attach.setPath(rs.getString(3));
                attach.setUploads(LocalDateTime.fromDateFields((rs.getTimestamp(4))));
                
                attach.setUserComment(rs.getString(5));
                attachments.add(attach);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeContactAttachments: {} ", e);
            throw new DAOException("Database error during takeContactAttachments.");
        }
        return attachments;
    }
	
	public void deleteByContact(int contactId) throws DAOException {
		logger.info("Start deleteByContact method with id of the contact: {}", contactId);
		try(PreparedStatement ps=connection.prepareStatement(SQL_DELETE_ALL_CONTACT_ATTACHMENTS)) {
            ps.setInt(1, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in delete: {}", e);
            throw new DAOException("Database error during attachment deleting.");
        }
	}
	
	
	@Override
	public void delete(Attachment entity) throws DAOException {
		logger.info("Start delete method, attachment id: {}", entity.getId());
		try(PreparedStatement ps=connection.prepareStatement(SQL_ATTACHMENT_DELETE)) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in delete: {}", e);
            throw new DAOException("Database error during attachment deleting.");
        }

	}

	@Override
	public int create(Attachment entity) throws DAOException {
		logger.info("Start create method");
		int result = 0;
		try(PreparedStatement ps=connection.prepareStatement(SQL_ATTACHMENT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, entity.getTitle());
			ps.setString(2, entity.getPath());
			ps.setTimestamp(3,new Timestamp(entity.getUploads().toDateTime(DateTimeZone.getDefault()).getMillis()));
			ps.setString(4, entity.getUserComment());
			ps.setInt(5, entity.getContact().getId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                result = rs.getInt(1);
            }
            logger.debug("Inserted row: {}", result);
            return result;
        } catch (SQLException e) {
        	logger.error("Exception in create: {}", e);
            throw new DAOException("Database error during attachment creating.");
        }

	}
	
	
	public void updatePath(Attachment entity) throws DAOException{
		logger.info("Start updatePath method");
		try(PreparedStatement ps=connection.prepareStatement(SQL_ATTACHMENT_UPDATE_PATH)) {
			ps.setString(1,entity.getPath());
			ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in updatePath: {}", e);
            throw new DAOException("Database error during attachment path update");
        }
	}

	@Override
	public void update(Attachment entity) throws DAOException {
		logger.debug("Start update method");
		try(PreparedStatement ps=connection.prepareStatement(SQL_ATTACHMENT_UPDATE)) {
			ps.setString(1,entity.getTitle());
			ps.setString(2, entity.getUserComment());
			ps.setInt(3, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        	logger.error("Exception in update: {}", e);
            throw new DAOException("Database error during attachment update");
        }
	}

}
