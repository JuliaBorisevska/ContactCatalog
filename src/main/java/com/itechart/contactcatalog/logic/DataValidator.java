package com.itechart.contactcatalog.logic;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DataValidator {
	public final static String REGEX_NAME="^[A-ZА-Я]{1}[a-zёа-я]{2,20}$";
	public final static String REGEX_NAME_SEARCH="^[A-ZЁА-Яa-zёа-я]{1,20}$";
    public final static String REGEX_CITIZENSHIP="^[A-ZА-Яa-zа-я]{2,20}$";
    public final static String REGEX_LAST_NAME="^[A-ZА-Я]{1}[a-zёа-я]{2,19}-?([A-ZА-Я]{1}[a-zёа-я]{2,18})?$";
    public final static String REGEX_EMAIL="^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public final static String REGEX_COUNTRY="^[A-ZА-ЯЁa-zёа-я\\s-]{2,20}$";
    public final static String REGEX_COMPANY="^[\\dA-ZА-ЯЁa-zёа-я\\s-]{2,20}$";
    public final static String REGEX_SITE="^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";
    public final static String REGEX_STREET="^[A-ZА-ЯЁa-zёа-я\\s-\\d\\.]{2,20}$";
    public final static String REGEX_INDEX="^\\d{6}$";
    public final static String REGEX_DIGIT="^\\d+$";
    public final static String REGEX_DATE="^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])$";
    public final static String REGEX_COUNTRY_CODE="^\\d{3}$";
    public final static String REGEX_OPERATOR_CODE="^\\d{2,3}$";
    public final static String REGEX_NUMBER="^\\d{5,7}$";
    public final static String REGEX_COMMENT="^[A-ZЁА-Яa-zёа-я\\s_,;:!?()\\d]{1,100}$"; 


    public static boolean validateContactData(String firstName, String lastName, String middleName, 
    		String date, String citizenship,  String site, String email, String company, String country, 
    		String town, String street, String house, String flat, String index){
    		return  Pattern.matches(REGEX_NAME, firstName) && Pattern.matches(REGEX_LAST_NAME, lastName)
                && StringUtils.isNotBlank(middleName)?Pattern.matches(REGEX_NAME, middleName):true
                && StringUtils.isNotBlank(email)?Pattern.matches(REGEX_EMAIL,email):true
                && Pattern.matches(REGEX_DATE, date) 
                && Pattern.matches(REGEX_COUNTRY, country)
                && Pattern.matches(REGEX_COUNTRY, town)
                && StringUtils.isNotBlank(house)?Pattern.matches(REGEX_DIGIT, house):true
                && StringUtils.isNotBlank(flat)?Pattern.matches(REGEX_DIGIT, flat):true
                && StringUtils.isNotBlank(citizenship)?Pattern.matches(REGEX_CITIZENSHIP, citizenship):true
                && StringUtils.isNotBlank(site)?Pattern.matches(REGEX_SITE, site):true
                && StringUtils.isNotBlank(company)?Pattern.matches(REGEX_COMPANY, company):true
                && StringUtils.isNotBlank(street)?Pattern.matches(REGEX_STREET, street):true
                && StringUtils.isNotBlank(index)?Pattern.matches(REGEX_INDEX, index):true;
    }
    
    public static boolean validateSearchDate(String firstName, String lastName, String middleName, 
    		String dateFrom, String dateTo, String citizenship, String country, String town, String street,
    		String house, String flat, String index){
    		return StringUtils.isNotBlank(middleName)?Pattern.matches(REGEX_NAME_SEARCH, middleName):true
                && StringUtils.isNotBlank(firstName)?Pattern.matches(REGEX_NAME_SEARCH,firstName):true
                && StringUtils.isNotBlank(lastName)?Pattern.matches(REGEX_NAME_SEARCH, lastName):true
                && StringUtils.isNotBlank(dateFrom)?Pattern.matches(REGEX_DATE, dateFrom):true
                && StringUtils.isNotBlank(dateTo)?Pattern.matches(REGEX_DATE, dateTo):true
                && StringUtils.isNotBlank(country)?Pattern.matches(REGEX_NAME_SEARCH, country):true
                && StringUtils.isNotBlank(town)?Pattern.matches(REGEX_NAME_SEARCH, town):true
                && StringUtils.isNotBlank(house)?Pattern.matches(REGEX_DIGIT, house):true
                && StringUtils.isNotBlank(flat)?Pattern.matches(REGEX_DIGIT, flat):true
                && StringUtils.isNotBlank(citizenship)?Pattern.matches(REGEX_NAME_SEARCH, citizenship):true
                && StringUtils.isNotBlank(street)?Pattern.matches(REGEX_STREET, street):true
                && StringUtils.isNotBlank(index)?Pattern.matches(REGEX_DIGIT, index):true;
    }
    
    public static boolean validatePhoneData(String countryCode, String operatorCode, String number, 
    		String comment){
        return Pattern.matches(REGEX_COUNTRY_CODE, countryCode) && Pattern.matches(REGEX_OPERATOR_CODE, operatorCode)
                && StringUtils.isNotBlank(comment)?Pattern.matches(REGEX_COMMENT, comment):true
                && Pattern.matches(REGEX_NUMBER, number);
    }
    
    public static boolean validateAttachmentData(String title, String comment){
        return Pattern.matches(REGEX_COMMENT, title) 
        		&& StringUtils.isNotBlank(comment)?Pattern.matches(REGEX_COMMENT, comment):true;
    }
    
    
}
