package com.itechart.contactcatalog.dao;

import java.sql.Connection;

import com.itechart.contactcatalog.subject.Entity;

public abstract class AbstractDAO <T extends Entity> {
	protected Connection connection;
	
	public AbstractDAO(Connection connection) {
        this.connection = connection;
    }
	
	public abstract boolean delete(T entity);
	public abstract boolean create(T entity);
	public abstract boolean update(T entity);

}
