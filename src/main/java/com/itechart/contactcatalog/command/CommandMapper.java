package com.itechart.contactcatalog.command;

import org.apache.log4j.Logger;


public class CommandMapper {
	private static Logger logger = Logger.getLogger(CommandMapper.class);

	private static ActionMapBuilder builder = new ActionMapBuilder();
	
	public CommandMapper() {
	}
	
	
	//могут вернуть null
	public ActionCommand defineCommand(String title) { 
		ActionCommand command = builder.getCommandMap().get(title);
		return command;
    }
	public String definePage(String title) { 
		String page = builder.getPageMap().get(title);
		return page;
    }
}
