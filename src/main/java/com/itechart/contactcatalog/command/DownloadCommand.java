package com.itechart.contactcatalog.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.controller.ContactServlet;
import com.itechart.contactcatalog.property.ConfigurationManager;

public class DownloadCommand implements ActionCommand {
	private static Logger logger = LoggerFactory.getLogger(DownloadCommand.class);
    
    private static final String FILE_NAME_PARAMETER = "file";
	
	
	@Override
	public boolean execute(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start DownloadCommand with request: {}", ContactServlet.takeRequestInformation(request));
		String fileName = request.getParameter(FILE_NAME_PARAMETER);
		logger.debug("Name of the file: {}", fileName);
		String filePath = ConfigurationManager.getProperty("folder.attach")+File.separator+fileName;
		File downloadFile = new File(filePath);
		try (FileInputStream inStream = new FileInputStream(downloadFile); 
				OutputStream outStream = response.getOutputStream()){
			ServletContext context = request.getSession().getServletContext();
			String mimeType = context.getMimeType(filePath);
			if (mimeType == null) {			
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);	
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			return true;
			} catch (IOException e) {
				request.setAttribute("customerror", "message.customerror");
		        logger.error("Exception in execute: {}", e);
		        return false;
			}
	}

}
