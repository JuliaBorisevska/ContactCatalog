package com.itechart.contactcatalog.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.subject.Contact;

public class TemplateCreator {
	private static Logger logger = LoggerFactory.getLogger(TemplateCreator.class);
	private VelocityEngine ve = new VelocityEngine();
	public static final String MESSAGE_ENCODING="UTF-8";
	
	public TemplateCreator(){
		 Properties props = new Properties();
		 props.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
		 props.setProperty("classpath."+VelocityEngine.RESOURCE_LOADER+".class",ClasspathResourceLoader.class.getName());
		 ve.init(props);
	}
	
	public String formMessage(Contact contact, String fileName){
	    logger.info("Start of formMessage method with parameters: contact - [{}], fileName - {}", contact, fileName);
	    Template t = ve.getTemplate(fileName, MESSAGE_ENCODING);
	    VelocityContext vc = new VelocityContext();
	    vc.put("contact", contact);
	    StringWriter writer = new StringWriter();
	    t.merge(vc, writer);
	    try {
			writer.close();
		} catch (IOException e) {
			logger.error("Error in formMessage: {}", e);
		}
		return writer.toString(); 

	}
	
   
	
}
