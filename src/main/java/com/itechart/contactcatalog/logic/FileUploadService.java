package com.itechart.contactcatalog.logic;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.exception.UploadException;

public class FileUploadService {
	private static Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    private static final String ATTACHMENT_UPLOAD_DIRECTORY = "upload";
    private static final String PHOTO_UPLOAD_DIRECTORY = "images";
    private static final String PHOTO_FILE_INPUT = "photo";
 
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    
    public static void uploadFile(FileItem item, String fileName) throws UploadException{
    	
    	DiskFileItemFactory factory = new DiskFileItemFactory();
    	factory.setSizeThreshold(MEMORY_THRESHOLD);
    	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

    	ServletFileUpload upload = new ServletFileUpload(factory);

    	upload.setFileSizeMax(MAX_FILE_SIZE);

    	upload.setSizeMax(MAX_REQUEST_SIZE);

    	String uploadPath;
    	if (PHOTO_FILE_INPUT.equals(item.getFieldName())){
    		uploadPath = ContactServlet.getPrefix()  + File.separator + PHOTO_UPLOAD_DIRECTORY;
    	}else{
    		uploadPath = ContactServlet.getPrefix()  + File.separator + ATTACHMENT_UPLOAD_DIRECTORY;
    	}
    	logger.debug("uploadPath: {}", uploadPath);

    	File uploadDir = new File(uploadPath);
    	if (!uploadDir.exists()) {
    		uploadDir.mkdir();
    	}
    	try {
    		String filePath = uploadPath + File.separator + fileName;
    		File storeFile = new File(filePath);
    		item.write(storeFile);
    				
    	} catch (Exception ex) {
    		logger.error("Error in uploadFile {}: {}", fileName, ex);
    		throw new UploadException("Error in uploadFile");
    	}
    }
    
    public static String getFileExtention(String fullPath){
    	int sepPos = fullPath.lastIndexOf(File.separator); 
    	String nameAndExt = fullPath.substring(sepPos + 1, fullPath.length()); 
    	int dotPos = nameAndExt.lastIndexOf("."); 
    	return dotPos!=-1 ? nameAndExt.substring(dotPos):""; 
    }
}
