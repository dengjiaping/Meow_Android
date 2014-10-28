package com.widen.http;

import org.json.JSONArray;
import org.json.JSONObject;
import com.widen.util.Constant;


public abstract class IHttpTask{


	public abstract String getSubUrl();

	/**
	 * 返回报文截取的字符串标题
	 * 
	 * @return
	 */
	public abstract String getLabel();

	/**
	 * 请求参数
	 * 
	 * @param params
	 */
	public abstract void setParams(String[] params);
	
	public abstract String getParams();
	
	public abstract String getType();
	
	public final String getContentType(){
	
		if(getType() == Constant.GET){
			return "application/x-www-form-urlencoded; charset=utf-8";
		}else if (getType() == Constant.POST){
			return "application/json; charset=utf-8";
		}
		return "";
	}
	
	public boolean isRalativeUrl(){
		return true;
	}
	
	public abstract Object getData(JSONObject obj)  throws Exception;
	
	public abstract Object getData(JSONArray obj)  throws Exception;
	
	
}
