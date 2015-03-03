package com.itechart.contactcatalog.helper;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.dao.ContactDAO;
import com.itechart.contactcatalog.dao.PhoneDAO;
import com.itechart.contactcatalog.exception.ConnectionPoolException;
import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.pool.ConnectionPool;
import com.itechart.contactcatalog.subject.MaritalStatus;
import com.itechart.contactcatalog.subject.PhoneType;
import com.itechart.contactcatalog.subject.Sex;

public class ViewHelper {
	private static Logger logger = LoggerFactory.getLogger(ViewHelper.class);

	public ArrayList<Sex> takeSexList(){
        ArrayList<Sex> sexList = new ArrayList<>();
        Connection conn = null;
        try {
			conn = ConnectionPool.getInstance().getConnection();
			ContactDAO dao = new ContactDAO(conn);
	        sexList = dao.takeSexList();
		}catch (ConnectionPoolException | DAOException e) {  //или выбрасывать на страницу?
        	logger.error("Exception in takeSexList: {} ", e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in takeSexList: {} ", e);
			}
        }
        return sexList;
    }
	
	public ArrayList<PhoneType> takePhoneTypes(){
        ArrayList<PhoneType> phoneTypes = new ArrayList<>();
        Connection conn = null;
        try {
			conn = ConnectionPool.getInstance().getConnection();
			PhoneDAO dao = new PhoneDAO(conn);
			phoneTypes = dao.takePhoneTypes();
		}catch (ConnectionPoolException | DAOException e) {  //или выбрасывать на страницу?
        	logger.error("Exception in takePhoneTypes: {} ", e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in takePhoneTypes: {} ", e);
			}
        }
        return phoneTypes;
    }
	
	public ArrayList<MaritalStatus> takeMaritalStatusList(){
        ArrayList<MaritalStatus> statusList = new ArrayList<>();
        Connection conn = null;
        try {
			conn = ConnectionPool.getInstance().getConnection();
			ContactDAO dao = new ContactDAO(conn);
			statusList = dao.takeMaritalStatusList();
		}catch (ConnectionPoolException | DAOException e) {  //или выбрасывать на страницу?
        	logger.error("Exception in takeMaritalStatusList: {} ", e);
		} finally {
        	try {
				ConnectionPool.getInstance().returnConnection(conn);
			} catch (ConnectionPoolException e) {
				logger.error("Exception in ttakeMaritalStatusList: {} ", e);
			}
        }
        return statusList;
    }

}
