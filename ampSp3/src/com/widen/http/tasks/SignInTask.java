package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.SignInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class SignInTask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/User/SignIn";
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
			jobject.put("Name", strs[0]);
			jobject.put("Password", strs[1]);
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
		
		SignInfo info = new SignInfo();
		info.Lock = Util.getJsonString(obj, "Lock");
		info.RemainCount = Util.getJsonString(obj, "RemainCount");
		info.Successful = Util.getJsonBoolean(obj, "Successful");
		info.UserExist = Util.getJsonBoolean(obj, "UserExist");
		return info;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
