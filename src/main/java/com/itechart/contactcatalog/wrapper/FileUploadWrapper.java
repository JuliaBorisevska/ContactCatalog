package com.itechart.contactcatalog.wrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.command.ContactChangeCommand;

public class FileUploadWrapper extends HttpServletRequestWrapper {  //изменить exception + лог
	private static Logger logger = LoggerFactory.getLogger(FileUploadWrapper.class); 
	private static final int FIRST_VALUE = 0;  
	private static final String ENCODING = "UTF-8";  
	private final Map<String, String[]> fRegularParams = new LinkedHashMap<>();
	private final Map<String, FileItem> fFileParams = new LinkedHashMap<>();

	
	
	  /** Constructor.  */
	  public FileUploadWrapper(HttpServletRequest aRequest) throws IOException {
	    super(aRequest);
	    ServletFileUpload upload = new ServletFileUpload( new DiskFileItemFactory());
	    try {
	      List<FileItem> fileItems = upload.parseRequest(aRequest);
	      convertToMaps(fileItems);
	    }
	    catch(FileUploadException ex){
	      throw new IOException("Cannot parse underlying request: " + ex.toString());
	    }
	  }
	  
	  
	  
	  @Override
	public Object getAttribute(String name) {
		return super.getAttribute(name);
	}



	@Override
	public Enumeration<String> getAttributeNames() {
		return super.getAttributeNames();
	}



	@Override
	public void setAttribute(String name, Object o) {
		super.setAttribute(name, o);
	}



	/**
	  * Return all request parameter names, for both regular controls and file upload 
	  * controls.
	  */
	  @Override 
	  public Enumeration<String> getParameterNames() {
	    Set<String> allNames = new LinkedHashSet<>();
	    allNames.addAll(fRegularParams.keySet());
	    allNames.addAll(fFileParams.keySet());
	    return Collections.enumeration(allNames);
	  }
	  
	  /**
	  * Return the parameter value. Applies only to regular parameters, not to 
	  * file upload parameters. 
	  * 
	  * <P>If the parameter is not present in the underlying request, 
	  * then <tt>null</tt> is returned.
	  * <P>If the parameter is present, but has no  associated value, 
	  * then an empty string is returned.
	  * <P>If the parameter is multivalued, return the first value that 
	  * appears in the request.  
	  */
	  @Override 
	  public String getParameter(String aName) {
	    String result = null;
	    String[] values = fRegularParams.get(aName);
	    if(values == null){
	    }
	    else if (values.length==0) {
	      result = "";
	    }
	    else {
	      result = values[FIRST_VALUE];
	    }
	    return result;
	  }
	  
	  /**
	  * Return the parameter values. Applies only to regular parameters, 
	  * not to file upload parameters.
	  */
	  @Override 
	  public String[] getParameterValues(String aName) {
		  return fRegularParams.get(aName);
	  }
	  
	  /**
	  * Return a {@code Map<String, String[]} for all regular parameters.
	  * Does not return any file upload parameters at all. 
	  */
	  @Override 
	  public Map<String, String[]> getParameterMap() {
	    return Collections.unmodifiableMap(fRegularParams);
	  }
	  
	  /**
	  * Return a {@code List<FileItem>}, in the same order as they appear
	  *  in the underlying request.
	  */
	  public List<FileItem> getFileItems(){
	    return new ArrayList<FileItem>(fFileParams.values());
	  }
	  
	  /**
	  * Return the {@link FileItem} of the given name.
	  * <P>If the name is unknown, then return <tt>null</tt>.
	  */
	  public FileItem getFileItem(String aFieldName){
	    return fFileParams.get(aFieldName);
	  }
	  
	  private void convertToMaps(List<FileItem> aFileItems){
	    for(FileItem item: aFileItems) {
	      if ( isFileUploadField(item) ) {
	        fFileParams.put(item.getFieldName(), item);
	      }
	      else {
	        if( alreadyHasValue(item) ){
	          addMultivaluedItem(item);
	        }
	        else {
	          addSingleValueItem(item);
	        }
	      }
	    }
	  }
	  
	  private boolean isFileUploadField(FileItem aFileItem){
	    return !aFileItem.isFormField();
	  }
	  
	  private boolean alreadyHasValue(FileItem aItem){
	    return fRegularParams.get(aItem.getFieldName()) != null;
	  }
	  
	  private void addSingleValueItem(FileItem aItem){              ///exception + log
		  String[] strarr = new String[1];
		try {
			strarr[0]=aItem.getString(ENCODING);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fRegularParams.put(aItem.getFieldName(), strarr);
	  }
	  
	  private void addMultivaluedItem(FileItem aItem){
		  String[] strings = fRegularParams.get(aItem.getFieldName());
          String[] strings2 = new String[1 + strings.length];
          System.arraycopy(strings, 0, strings2, 0, strings.length);
          try {
			strings2[strings.length] = aItem.getString(ENCODING);
			fRegularParams.put(aItem.getFieldName(), strings2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	} 
