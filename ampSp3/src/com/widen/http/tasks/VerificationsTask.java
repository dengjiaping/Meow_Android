package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.VerifyInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class VerificationsTask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/VeriCodes/Send";
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
			jobject.put("Cellphone", strs[0]);
			jobject.put("Type", strs[1]);
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
		VerifyInfo info = new VerifyInfo();
		info.Successful = Util.getJsonBoolean(obj, "Successful");
		info.remainCount = Util.getJsonInt(obj, "RemainCount");
		return info;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
