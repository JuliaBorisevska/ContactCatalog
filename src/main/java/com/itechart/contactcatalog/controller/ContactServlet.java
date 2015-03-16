package com.itechart.contactcatalog.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.command.ActionCommand;
import com.itechart.contactcatalog.command.CommandMapper;
import com.itechart.contactcatalog.observing.BirthdayChecker;
import com.itechart.contactcatalog.property.ConfigurationManager;

/**
 * Servlet implementation class ContactServlet
 */
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String prefix;
	private BirthdayChecker checker;
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
        checker = new BirthdayChecker();
        checker.start();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		checker.setCheck(false);
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
		logger.info("Start processRequest with request servlet path {} and path info {}",request.getServletPath(), request.getPathInfo());
		String page = null;
		Pattern separator = Pattern.compile("/");
		String [] parts = separator.split(request.getServletPath());
		CommandMapper mapper = new CommandMapper();
		ActionCommand command = mapper.defineCommand(parts[parts.length-1]);   
		page=mapper.definePage(parts[parts.length-1]);
		logger.debug("Command: {}, page: {}", command, page);
		boolean isSuccess = false;
		if (command!=null){
			if (StringUtils.isEmpty(page)){
				isSuccess=command.execute(request, response);
			}else if (isSuccess=command.execute(request, response)){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}
		}
		else{
			request.setAttribute("customerror", "message.customerror");
		}
		if (!isSuccess){
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ConfigurationManager.getProperty("path.page.customerror"));
			dispatcher.forward(request, response);
		}
		
	}
	
	public static String takeRequestInformation(HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String paramName=params.nextElement();
            String value=request.getParameter(paramName);
            sb.append("Parameter: ");
            sb.append(paramName);
            sb.append(" - ");
            sb.append(value);
            sb.append("\n");
        }
        Enumeration<String> attrs=request.getAttributeNames();
        while (attrs.hasMoreElements()){
            String attrName=attrs.nextElement();
            Object attrValue=request.getAttribute(attrName);
            sb.append("Attribute: ");
            sb.append(attrName);
            sb.append(" - ");
            sb.append(attrValue);
            sb.append(" ");
        }
        Enumeration<String> sessionAttrs=request.getSession().getAttributeNames();
        while (sessionAttrs.hasMoreElements()){
            String sessionName=sessionAttrs.nextElement();
            Object sessionValue=request.getSession().getAttribute(sessionName);
            sb.append("Session attribute: ");
            sb.append(sessionName);
            sb.append(" - ");
            sb.append(sessionValue);
            sb.append(" ");
        }
        return sb.toString();
        
	}

}
	
	
