package com.widen.http;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.parsers.AbsHandler;


public interface IHttpTask <T> {

	//public static boolean notEncode = false;
	/**
	 * 请求&lti n&gt...&lt/i&gt部分 字符串
	 * 
	 * @return
	 */
	String getSubUrl();

	/**
	 * 返回报文截取的字符串标题
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * 请求参数
	 * 
	 * @param params
	 */
	void setParams(String[] params);
	
	String getParams();
	
	String getType();
	
	String getContentType();
	
	Object getData(JSONObject obj)  throws Exception;
	
	Object getData(JSONArray obj)  throws Exception;
	
	boolean isNewApi();

}
