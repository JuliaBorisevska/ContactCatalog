package com.itechart.contactcatalog.command;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class SessionRequestContent {

	private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public SessionRequestContent() {
        requestAttributes=new HashMap<>();
        requestParameters=new HashMap<>();
        sessionAttributes=new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String paramName=params.nextElement();
            String values[]=request.getParameterValues(paramName);
            requestParameters.put(paramName,values);
        }
        Enumeration<String> attrs=request.getAttributeNames();
        while (attrs.hasMoreElements()){
            String attrName=attrs.nextElement();
            Object attrValue=request.getAttribute(attrName);
            requestAttributes.put(attrName,attrValue);
        }
        Enumeration<String> sessionAttrs=request.getSession().getAttributeNames();
        while (sessionAttrs.hasMoreElements()){
            String sessionName=sessionAttrs.nextElement();
            Object sessionValue=request.getSession().getAttribute(sessionName);
            sessionAttributes.put(sessionName,sessionValue);
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        Set<String> attrNames;
        attrNames= requestAttributes.keySet();
        for (String key : attrNames){
            request.setAttribute(key, requestAttributes.get(key));
        }
        Set<String> sessionNames;
        sessionNames= sessionAttributes.keySet();
        for (String key : sessionNames){
            request.getSession().setAttribute(key, sessionAttributes.get(key));
        }
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }
}
