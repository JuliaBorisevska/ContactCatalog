package com.itechart.contactcatalog.dao;

import java.sql.Connection;

import com.itechart.contactcatalog.exception.DAOException;
import com.itechart.contactcatalog.subject.Entity;

public abstract class AbstractDAO <T extends Entity> {
	protected Connection connection;
	
	public AbstractDAO(Connection connection) {
        this.connection = connection;
    }
	
	public abstract void delete(T entity) throws DAOException;
	public abstract int create(T entity) throws DAOException;
	public abstract void update(T entity) throws DAOException;

}
