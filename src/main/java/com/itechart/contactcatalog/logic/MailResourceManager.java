package com.itechart.contactcatalog.logic;

import java.util.ResourceBundle;

public class MailResourceManager {
	private  static ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
    private MailResourceManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
