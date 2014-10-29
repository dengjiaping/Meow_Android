package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.UserInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class UsersInfoTask extends IHttpTask{
	private String[] strs;

	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/UserInfo";
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
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
	
		UserInfo userInfo = new UserInfo();

		
	
		userInfo.bankCardNo = Util.getJsonString(obj, "BankCardNo");
		
		userInfo.bankCardsCount = Util.getJsonInt(obj, "BankCardsCount");
		userInfo.cardBankName = Util.getJsonString(obj, "CardBankName");
		userInfo.bankCardNo = Util.getJsonString(obj, "BankCardNo");
		userInfo.cellPhone = Util.getJsonString(obj, "Cellphone");
		userInfo.credential = Util.getJsonString(obj, "Credential");
		userInfo.credentialNo = Util.getJsonString(obj, "CredentialNo");
		userInfo.hasDefaultBankCard = Util.getJsonBoolean(obj, "HasDefaultBankCard");
		userInfo.hasSetPaymentPassword = Util.getJsonBoolean(obj, "HasSetPaymentPassword");
		userInfo.hasYSBInfo = Util.getJsonBoolean(obj, "HasYSBInfo");
		userInfo.loginName = Util.getJsonString(obj, "LoginName");
		userInfo.realName = Util.getJsonString(obj, "RealName");
		userInfo.signUpName = Util.getJsonString(obj, "SignUpTime");
		userInfo.verified = Util.getJsonBoolean(obj, "Verified");
		userInfo.verifing = Util.getJsonBoolean(obj, "Verifing");
		userInfo.YSBIdCard = Util.getJsonString(obj, "YSBIdCard");
		userInfo.YSBRealName = Util.getJsonString(obj, "YSBRealName");
		
		
		return userInfo;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
