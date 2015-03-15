package com.itechart.contactcatalog.template;



import com.itechart.contactcatalog.property.MailResourceManager;

public enum TemplateType {
	BIRTHDAY(MailResourceManager.getProperty("birthday.template"), MailResourceManager.getProperty("birthday.label")), 
	THANKS(MailResourceManager.getProperty("thanks.template"), MailResourceManager.getProperty("thanks.label")), 
	INFORMATION(MailResourceManager.getProperty("information.template"), MailResourceManager.getProperty("information.label"));
	
	private String template;
	private String title;
	
	private TemplateType(String template, String title){
		this.template=template;
		this.title=title;
		
	}

	public String getTemplate() {
		return template;
	}

	public String getTitle() {
		return title;
	}
	
	public String takeText(){
		TemplateCreator creator = new TemplateCreator();
		return creator.formMessage(null, template);
	}
}
