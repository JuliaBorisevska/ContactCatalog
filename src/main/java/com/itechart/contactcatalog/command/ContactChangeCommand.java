package com.itechart.contactcatalog.command;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactChangeCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(ContactChangeCommand.class);

	@Override
	public boolean execute(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Start ContactChangeCommand ");
		Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String paramName=params.nextElement();
            String value=request.getParameter(paramName);
            logger.debug("Parameter: " + paramName + ","+ value);
        }
        Enumeration<String> attrs=request.getAttributeNames();
        while (attrs.hasMoreElements()){
            String attrName=attrs.nextElement();
            Object attrValue=request.getAttribute(attrName);
            logger.debug("Attribute: name - {}, value - {}", attrName, attrValue);
        }
		return true;
	}

}
