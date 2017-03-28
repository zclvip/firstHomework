package com.zcl.work.request;

import java.util.HashMap;
import java.util.Map;

public class UrlParameter {
	private Map<String, String> paramMap;
	public UrlParameter(String params){
		if(null == params){
			return;
		}
	    String[] queryStringSplit = params.split("&");
	    paramMap = new HashMap<String,String>(queryStringSplit.length);
	    String[] queryStringParam;
	    for (String qs : queryStringSplit) {
	        queryStringParam = qs.split("=");
	        paramMap.put(queryStringParam[0], queryStringParam[1]);
	    }
	    System.out.println(paramMap);
	}
	
	public String getParameter(String key){
		return paramMap.get(key);
	}
	
	public static void main(String[] args){
		String queryString = "a=1&b=2&c=3&d=4";
	    String[] queryStringSplit = queryString.split("&");
	    Map<String,String> queryStringMap =
	            new HashMap<String,String>(queryStringSplit.length);
	    String[] queryStringParam;
	    for (String qs : queryStringSplit) {
	        queryStringParam = qs.split("=");
	        queryStringMap.put(queryStringParam[0], queryStringParam[1]);
	    }
	    System.out.println(queryStringMap);
	}

}
