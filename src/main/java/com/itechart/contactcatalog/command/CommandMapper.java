package com.itechart.contactcatalog.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandMapper {
	private static Logger logger = LoggerFactory.getLogger(CommandMapper.class);

	private static ActionMapBuilder builder = new ActionMapBuilder();
	
	public CommandMapper() {
	}
	
	public ActionCommand defineCommand(String title) { 
		logger.info("Start defineCommand method with parameter - {}", title);
		ActionCommand command = builder.getCommandMap().get(title);
		logger.info("End of defineCommand method with result - {}", command);
		return command;
    }
	public String definePage(String title) { 
		logger.info("Start definePage method with parameter - {}", title);
		String page = builder.getPageMap().get(title);
		logger.info("End of defineCommand method with result - {}", page);
		return page;
    }
}
