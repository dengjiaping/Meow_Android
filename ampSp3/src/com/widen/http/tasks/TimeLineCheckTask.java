package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.application.MyApplication;
import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.TimeLineInfo;
import com.widen.product.main.TimeLineFragment;
import com.widen.util.Constant;
import com.widen.util.Util;

public class TimeLineCheckTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/TimeLine/Check";
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
		return String.format("num=%s&timestamp=%s",strs[0],strs[1]);
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
		TimeLineFragment.needRefresh = Util.getJsonBoolean(obj, "Updated");	
		return Util.getJsonBoolean(obj, "Updated");
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
