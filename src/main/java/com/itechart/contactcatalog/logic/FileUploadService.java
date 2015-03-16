package com.itechart.contactcatalog.logic;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.exception.UploadException;
import com.itechart.contactcatalog.property.ConfigurationManager;

public class FileUploadService {
	private static Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    private static final String ATTACHMENT_UPLOAD_DIRECTORY = ConfigurationManager.getProperty("folder.attach");
    private static final String PHOTO_UPLOAD_DIRECTORY = ConfigurationManager.getProperty("folder.image.relative");
    private static final String PHOTO_FILE_INPUT = "photo";
    
    public static void uploadFile(FileItem item, String fileName) throws UploadException{
    	String uploadPath;
    	if (PHOTO_FILE_INPUT.equals(item.getFieldName())){
    		uploadPath = ContactServlet.getPrefix()  + File.separator + PHOTO_UPLOAD_DIRECTORY;
    	}else{
    		uploadPath = ATTACHMENT_UPLOAD_DIRECTORY;
    	}
    	logger.info("Start of uploadFile, uploadPath: {}", uploadPath);

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
    
    public static boolean deleteUpload(String fileName){
    	logger.info("Start of deleteUpload with fileName - {}", fileName);
    	boolean result = false;
    	String filePath = ATTACHMENT_UPLOAD_DIRECTORY + File.separator + fileName;
    	File file = new File(filePath);
        if (file.exists()){
            result = file.delete();
        }else{
        	logger.warn("File {} doesn't exist", filePath);
        }
        return result;
    }
}
