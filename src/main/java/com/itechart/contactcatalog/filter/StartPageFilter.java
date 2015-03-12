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
import javax.servlet.http.HttpServletResponse;

import com.itechart.contactcatalog.command.ActionCommand;
import com.itechart.contactcatalog.command.ContactListCommand;
import com.itechart.contactcatalog.dao.ContactDAO;

/**
 * Servlet Filter implementation class StartPageFilter
 */
public class StartPageFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		req.getSession().setAttribute("contactListStatement", ContactDAO.getStatementForContactsList());
		req.getSession().setAttribute("contactCountStatement", ContactDAO.getStatementForContactsCount());
		ActionCommand command = new ContactListCommand();
        command.execute(req, resp);
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
