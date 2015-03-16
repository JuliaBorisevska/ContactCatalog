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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.property.ConfigurationManager;
import com.itechart.contactcatalog.wrapper.FileUploadWrapper;

/**
 * Servlet Filter implementation class FileUploadFilter
 */
public class FileUploadFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(FileUploadFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
	    if ( isFileUploadRequest(req) ) {
	    	FileUploadWrapper wrapper;
			try {
				wrapper = new FileUploadWrapper(req);
				chain.doFilter(wrapper, response);
			} catch (IOException e) {
				logger.error("Exception during FileUploadWrapper construction: {}", e);
				request.setAttribute("customerror", "message.attach.failed");
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(ConfigurationManager.getProperty("path.page.customerror"));
				dispatcher.forward(request, response);
			}
	    }
	    else {
	      chain.doFilter(request, response);
	    }
	}
	
	private boolean isFileUploadRequest(HttpServletRequest request){
	    return request.getMethod().equalsIgnoreCase("POST") && 
	      request.getContentType().startsWith("multipart/form-data");
	  }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
