package com.itechart.contactcatalog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.itechart.contactcatalog.command.ActionCommand;
import com.itechart.contactcatalog.command.ContactListCommand;
import com.itechart.contactcatalog.command.SessionRequestContent;

/**
 * Servlet Filter implementation class StartPageFilter
 */
public class StartPageFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
        ActionCommand command = new ContactListCommand();
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.extractValues(req);
        command.execute(requestContent);
        requestContent.insertAttributes(req);
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
