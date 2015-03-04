package com.itechart.contactcatalog.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface ActionCommand {
	boolean execute(HttpServletRequest request, HttpServletResponse response);
}
