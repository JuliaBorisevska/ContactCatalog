package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Attachment;
import com.itechart.contactcatalog.subject.Phone;



public class AttachmentDAO extends AbstractDAO<Attachment> {
	private static Logger logger = LoggerFactory.getLogger(AttachmentDAO.class);
	
	private final static String SQL_SELECT_CONTACT_ATTACHMENTS="SELECT attachment.* FROM attachment JOIN contact ON attachment.contact_id=contact.id WHERE contact.id=?";
	private final static String SQL_ATTACHMENT_UPDATE="UPDATE attachment SET title=?, user_comment=? WHERE id=?"; 
	private final static String SQL_ATTACHMENT_DELETE="DELETE FROM attachment WHERE id=?";
	private final static String SQL_ATTACHMENT_INSERT = "INSERT INTO attachment (title, path, upload_date, user_comment, contact_id) VALUES (?, ?, ?, ?, ?)";
	private final static String SQL_SELECT_ATTACHMENT_FOR_DELETE_TEMPLATE = "SELECT id FROM attachment WHERE id NOT IN (%s) AND contact_id=?";
	
	public AttachmentDAO(Connection connection) {
		super(connection);
	}

	public ArrayList<Attachment> takeAttachmentsForDelete(ArrayList<Attachment> attachs) throws DAOException {
		logger.debug("Start takeContactPhonesForDelete method");
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
            	attachments.add(attachment);
            }
        } catch (SQLException e) {
        	logger.error("Exception in takeAttachmentsForDelete: {} ", e);
            throw new DAOException("Database error during takeAttachmentsForDelete.");
        }
        return attachments;
    }
	
	public ArrayList<Attachment> takeContactAttachments(int contactId) throws DAOException {
		logger.debug("Start takeContactAttachments method");
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
            throw new DAOException("Database error during takeContactAttachments. Try to make your request later.");
        }
        return attachments;
    }
	
	
	
	
	@Override
	public void delete(Attachment entity) throws DAOException {
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
		try(PreparedStatement ps=connection.prepareStatement(SQL_ATTACHMENT_INSERT)) {
			ps.setString(1, entity.getTitle());
			ps.setString(2, entity.getPath());
			ps.setTimestamp(3,new Timestamp(entity.getUploads().toDateTime().getMillis()));
			ps.setString(5, entity.getUserComment());
			ps.setInt(6, entity.getContact().getId());
            int result = ps.executeUpdate();
            logger.debug("Inserted row: {}", result);
            return result;
        } catch (SQLException e) {
        	logger.error("Exception in create: {}", e);
            throw new DAOException("Database error during attachment creating.");
        }

	}

	@Override
	public void update(Attachment entity) throws DAOException {
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
