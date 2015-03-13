package com.itechart.contactcatalog.logic;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DataValidator {
	public final static String REGEX_NAME="[A-ZА-Я]{1}[a-zа-я]{2,20}";
    public final static String REGEX_EMAIL="^[-\\w.]+@([A-z0-9][-A-z0-9]+.)+[A-z]{2,4}$";
    public final static String REGEX_COUNTRY="[A-ZА-Яa-zа-я\\p{Blank}]{2,20}";
    public final static String REGEX_COMPANY="[\\dA-ZА-Яa-zа-я\\p{Blank}]{2,20}";
    public final static String REGEX_SITE="[\\w.\\d]{5,20}";
    public final static String REGEX_INDEX="\\d{6}";
    public final static String REGEX_DIGIT="\\d+";
    public final static String REGEX_DATE="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
    public final static String REGEX_COUNTRY_CODE="\\d{3}";
    public final static String REGEX_OPERATOR_CODE="\\d{2-3}";
    public final static String REGEX_NUMBER="\\d{5-7}";
    public final static String REGEX_COMMENT="[A-ZА-Яa-zа-я\\s_;:!?\\\\d]{2,90}";


    public static boolean validateContactData(String firstName, String lastName, String middleName, 
    		String email, String date, String citizenship, String site, String company, String country, String street,
    		String town, String house, String flat){
        return StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName) && StringUtils.isNotBlank(date)
                && StringUtils.isNotBlank(country) && StringUtils.isNotBlank(town)
                && Pattern.matches(REGEX_NAME, firstName) && Pattern.matches(REGEX_NAME, lastName)
                && StringUtils.isNotBlank(middleName)?Pattern.matches(REGEX_NAME, middleName):true
                && StringUtils.isNotBlank(email)?Pattern.matches(REGEX_EMAIL,email):true
                && Pattern.matches(REGEX_DATE, date) 
                && Pattern.matches(REGEX_COUNTRY, country)
                && Pattern.matches(REGEX_COUNTRY, town)
                && StringUtils.isNotBlank(house)?Pattern.matches(REGEX_DIGIT, house):true
                && StringUtils.isNotBlank(flat)?Pattern.matches(REGEX_DIGIT, middleName):true
                && StringUtils.isNotBlank(citizenship)?Pattern.matches(REGEX_COUNTRY, citizenship):true
                && StringUtils.isNotBlank(site)?Pattern.matches(REGEX_SITE, middleName):true
                && StringUtils.isNotBlank(company)?Pattern.matches(REGEX_COMPANY, company):true
                && StringUtils.isNotBlank(street)?Pattern.matches(REGEX_COUNTRY, street):true;
    }
    
    public static boolean validatePhoneData(String countryCode, String operatorCode, String number, 
    		String comment){
        return StringUtils.isNotBlank(countryCode) && StringUtils.isNotBlank(operatorCode) && StringUtils.isNotBlank(number)
                && Pattern.matches(REGEX_COUNTRY_CODE, countryCode) && Pattern.matches(REGEX_OPERATOR_CODE, operatorCode)
                && StringUtils.isNotBlank(comment)?Pattern.matches(REGEX_COMMENT, comment):true
                && Pattern.matches(REGEX_NUMBER, number) ;
    }
    
    public static boolean validateAttachmentData(String title, String comment){
        return StringUtils.isNotBlank(title) 
        		&& Pattern.matches(REGEX_COMPANY, title) 
        		&& StringUtils.isNotBlank(comment)?Pattern.matches(REGEX_COMMENT, comment):true;
    }
    
    
}
