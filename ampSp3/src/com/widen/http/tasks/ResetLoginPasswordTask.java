package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.util.Constant;

public class ResetLoginPasswordTask  extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/User/ResetLoginPassword";
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return getSubUrl() + "_" + getType();
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		strs = params;
	}

	@Override
	public String getParams() {
		try{
			
			JSONObject jobject = new JSONObject();
			jobject.put("Password",strs[0]);
			jobject.put("Token",strs[1]);
			
			return jobject.toString();
			
		}catch(Exception e){
			return "";
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.POST;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
