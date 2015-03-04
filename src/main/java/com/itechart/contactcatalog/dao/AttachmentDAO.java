package com.itechart.contactcatalog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Attachment;



public class AttachmentDAO extends AbstractDAO<Attachment> {
	private static Logger logger = LoggerFactory.getLogger(AttachmentDAO.class);
	
	private final static String SQL_SELECT_CONTACT_ATTACHMENTS="SELECT attachment.* FROM attachment JOIN contact ON attachment.contact_id=contact.id WHERE contact.id=?";
	
	public AttachmentDAO(Connection connection) {
		super(connection);
	}

	public ArrayList<Attachment> takeContactAttachments(int contactId) throws DAOException {
		logger.debug("Start takeContactAttachments method");
		ArrayList<Attachment> attachments = new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(SQL_SELECT_CONTACT_ATTACHMENTS)) {
        	ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Attachment attach = new Attachment();
            	attach.setId(rs.getInt(1));
                attach.setTitle(rs.getString(2));
                attach.setPath(rs.getString(3));
                attach.setUploads(rs.getString(4));
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
	public boolean delete(Attachment entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Attachment entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Attachment entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
