package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.UpdateInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class CheckMobileVersionTask extends IHttpTask{

	private String[] strs;
	
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/app/upgradeex";
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
		return String.format("channel=%s&source=%s&v=%s",strs[0],strs[1],strs[2]);
	}

	@Override
	public String getType() {
		
		return Constant.GET;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
			
		UpdateInfo info = new UpdateInfo();
		info.status = Util.getJsonString(obj, "status");
		info.url = Util.getJsonString(obj, "url");
		info.message = Util.getJsonString(obj, "message");
		return info;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
