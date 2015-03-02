package com.itechart.contactcatalog.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itechart.contactcatalog.controller.ContactServlet;

public class ActionMapBuilder {

	private static Logger logger = LoggerFactory.getLogger(ActionMapBuilder.class);

	private Map<String, ActionCommand> commandMap;
	private Map<String, String> pageMap;
	private DocumentBuilder docBuilder;
	
	public ActionMapBuilder() {
		logger.debug("Action Map Builder constructor");
		this.commandMap = new HashMap<String, ActionCommand>();
		this.pageMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
			fillMaps(ContactServlet.getPrefix()+"/WEB-INF/classes/actions.xml");
		} catch (ParserConfigurationException e) {
			logger.error("Parser configuration error: " + e);
		}
	}
	
	public Map<String, ActionCommand> getCommandMap() {
		return commandMap;
	}


	public Map<String, String> getPageMap() {
		return pageMap;
	}

	private void fillMaps(String fileName) {
		Document doc = null;
		try {
			doc = docBuilder.parse(fileName);
			Element root = doc.getDocumentElement();
			NodeList studentsList = root.getElementsByTagName("action");
			for (int i = 0; i < studentsList.getLength(); i++) {
				Element studentElement = (Element) studentsList.item(i);
				String title = getElementTextContent(studentElement, "title");
				String className = getElementTextContent(studentElement, "class");
				String jsp = getElementTextContent(studentElement, "jsp");
				commandMap.put(title, (ActionCommand) Class.forName(className).newInstance());
				pageMap.put(title, jsp);
			}
		} catch (IOException e) {
			logger.error("File error or I/O error: " + e);
		} catch (SAXException e) {
			logger.error("Parsing failure: " + e);
		} catch (InstantiationException e) {
			logger.error("InstantiationException " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException " + e.getMessage());
		} 


	}
	
	private static String getElementTextContent(Element element, String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}
}
