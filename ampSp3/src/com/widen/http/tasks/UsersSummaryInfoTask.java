package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.UserInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class UsersSummaryInfoTask implements IHttpTask{
	private String[] strs;

	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Users/SummaryInfo";
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return getSubUrl() + "_" + getParams();
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		strs = params;
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
	
		UserInfo userInfo = new UserInfo();

		JSONObject account = Util.getJsonObj(obj, "Account");
	
		userInfo.Earnings = Util.getJsonString(account, "Earnings");
		
		userInfo.ExpectedEarnings = Util.getJsonString(account, "ExpectedEarnings");
		
		userInfo.InvestingPrice = Util.getJsonString(account, "InvestingPrice");
	
		
		JSONObject User = Util.getJsonObj(obj, "User");
		
		userInfo.Cellphone = Util.getJsonString(User, "Cellphone");
		userInfo.IdCard = Util.getJsonString(User, "IdCard");
		userInfo.RealName = Util.getJsonString(User, "RealName");
		
		
		userInfo.Verified = Util.getJsonBoolean(obj, "Verified");	
System.out.println("end");		
		return userInfo;
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
