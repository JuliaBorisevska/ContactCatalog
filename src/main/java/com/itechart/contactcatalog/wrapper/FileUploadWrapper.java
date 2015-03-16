package com.itechart.contactcatalog.wrapper;

import java.io.File;
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


public class FileUploadWrapper extends HttpServletRequestWrapper {  
	private static Logger logger = LoggerFactory.getLogger(FileUploadWrapper.class); 
	private static final int FIRST_VALUE = 0;  
	private static final String ENCODING = "UTF-8";  
	private final Map<String, String[]> fRegularParams = new LinkedHashMap<>();
	private final Map<String, FileItem> fFileParams = new LinkedHashMap<>();
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	
	
	  public FileUploadWrapper(HttpServletRequest aRequest) throws IOException {
	    super(aRequest);
	    DiskFileItemFactory factory = new DiskFileItemFactory();
    	factory.setSizeThreshold(MEMORY_THRESHOLD);
    	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	upload.setFileSizeMax(MAX_FILE_SIZE);
    	upload.setSizeMax(MAX_REQUEST_SIZE);
	    try {
	      List<FileItem> fileItems = upload.parseRequest(aRequest);
	      convertToMaps(fileItems);
	    }
	    catch(FileUploadException ex){
	    	logger.error("Exception in FileUploadWrapper constructor: {}", ex);
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


	  @Override 
	  public Enumeration<String> getParameterNames() {
	    Set<String> allNames = new LinkedHashSet<>();
	    allNames.addAll(fRegularParams.keySet());
	    allNames.addAll(fFileParams.keySet());
	    return Collections.enumeration(allNames);
	  }
	  
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
	  
	  @Override 
	  public String[] getParameterValues(String aName) {
		  return fRegularParams.get(aName);
	  }
	  
	  @Override 
	  public Map<String, String[]> getParameterMap() {
	    return Collections.unmodifiableMap(fRegularParams);
	  }
	  
	  public List<FileItem> getFileItems(){
	    return new ArrayList<FileItem>(fFileParams.values());
	  }
	  
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
	  
	  private void addSingleValueItem(FileItem aItem){              
		  String[] strarr = new String[1];
		try {
			strarr[0]=aItem.getString(ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception in addSingleValueItem: {}", e);
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
			logger.error("Exception in addSingleValueItem: {}", e);
		}
	  }
	} 
