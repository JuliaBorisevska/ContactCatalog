package com.itechart.contactcatalog.logic;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;

public class FileUploadService {
	private static Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    
    public static void uploadFile(FileItem item, String fileName){
    	
    	byte[] fileBytes = item.get();
    	if (fileBytes.length==0){
    		return;
    	}
    	// configures upload settings
    	DiskFileItemFactory factory = new DiskFileItemFactory();
    	// sets memory threshold - beyond which files are stored in disk
    	factory.setSizeThreshold(MEMORY_THRESHOLD);
    	// sets temporary location to store files
    	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

    	ServletFileUpload upload = new ServletFileUpload(factory);
     
    	// sets maximum size of upload file
    	upload.setFileSizeMax(MAX_FILE_SIZE);
     
    	// sets maximum size of request (include file + form data)
    	upload.setSizeMax(MAX_REQUEST_SIZE);

    	// constructs the directory path to store upload file
    	// this path is relative to application's directory
    	String uploadPath = ContactServlet.getPrefix()  + File.separator + UPLOAD_DIRECTORY;
    	logger.debug("uploadPath: {}", uploadPath);
    	// creates the directory if it does not exist
    	File uploadDir = new File(uploadPath);
    	if (!uploadDir.exists()) {
    		uploadDir.mkdir();
    	}
    	try {
    		//String fileName = new File(item.getName()).getName();
    		String filePath = uploadPath + File.separator + fileName;
    		File storeFile = new File(filePath);
    		// saves the file on disk
    		item.write(storeFile);
    		//request.setAttribute("message","Upload has been done successfully!");
    				
    	} catch (Exception ex) {
    		logger.error("Error in uploadFile: {}", ex);
    		//request.setAttribute("message","There was an error: " + ex.getMessage());
    	}
    	// redirects client to message page
    	//getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
    
    public static String getFileExtention(String fullPath){
    	int sepPos = fullPath.lastIndexOf(File.separator); 
    	String nameAndExt = fullPath.substring(sepPos + 1, fullPath.length()); 
    	int dotPos = nameAndExt.lastIndexOf("."); 
    	return dotPos!=-1 ? nameAndExt.substring(dotPos):""; 
    }
}
