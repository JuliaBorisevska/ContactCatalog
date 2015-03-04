package com.itechart.contactcatalog.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.command.ActionCommand;
import com.itechart.contactcatalog.command.CommandMapper;

/**
 * Servlet implementation class ContactServlet
 */
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String prefix;
	private static Logger logger = LoggerFactory.getLogger(ContactServlet.class);
	
	
	public static String getPrefix() {
		return prefix;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		prefix = getServletContext().getRealPath("/");
        String filename = getInitParameter("init_log4j");
        if (filename != null) {
            PropertyConfigurator.configure(prefix+filename);
        }
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Start processRequest");
		logger.debug("Path info: {}", request.getServletPath());
		logger.debug("Path info: {}", request.getPathInfo());
		String page = null;
		Pattern separator = Pattern.compile("/");
		String [] parts = separator.split(request.getServletPath());
		CommandMapper mapper = new CommandMapper();
		ActionCommand command = mapper.defineCommand(parts[parts.length-1]);  //проверка на null 
		page=mapper.definePage(parts[parts.length-1]);
		if (command!=null && page!=null ){
			if (command.execute(request, response)){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/customerror.jsp");
				dispatcher.forward(request, response);
			}
		}else{
			request.setAttribute("customerror", "Null command or page");
			logger.error("Command: {}, page: {}", command, page);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/customerror.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}


}
	
	
