package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.ItemsCountInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ItemsCountTask implements IHttpTask{

	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Items/Count";
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return getSubUrl() + "_" + getParams();
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		
		ItemsCountInfo info = new ItemsCountInfo();
		info.Count = Util.getJsonString(obj, "Count");
		info.New = Util.getJsonBoolean(obj, "New");
		return info;
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

}
