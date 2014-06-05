package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.util.Constant;
import com.widen.util.Util;

public class VerificationsTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Verification/Send";
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
		// TODO Auto-generated method stub
		return String.format("Type=%s&Cellphone=%s&Code=null",strs[0],strs[1]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.POST;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		return Util.getJsonBoolean(obj, "Successful");
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNewApi() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

}
