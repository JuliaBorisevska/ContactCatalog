package com.itechart.contactcatalog.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.subject.Phone;
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
	    	FileUploadWrapper wrapper = new FileUploadWrapper(req);
	      chain.doFilter(wrapper, response);
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
