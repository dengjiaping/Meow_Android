package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ProductionsInfo2;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ProductsDetailNewTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Amp/Products";
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
		return String.format("Id=%s",strs[0]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		ProductionsInfo2 info = new ProductionsInfo2();
		
		info.BankName = Util.getJsonString(obj, "BankName");
		info.DueDate = Util.getJsonString(obj, "DueDate").split("T")[0];
		info.Duration = Util.getJsonString(obj, "Duration");
		info.FundedNumber = Util.getJsonString(obj, "FundedNumber");
		info.FundedPercentage = Util.getJsonString(obj, "FundedPercentage");
		info.IsRecommand = Util.getJsonString(obj, "IsRecommand");
		info.MaxNumber = Util.getJsonString(obj, "MaxNumber");
		info.MinNumber = Util.getJsonString(obj, "MinNumber");
		info.Name = Util.getJsonString(obj, "Name");
		
		info.ProductIdentifier = Util.getJsonString(obj, "ProductIdentifier");
		info.PubBegin = Util.getJsonString(obj, "PubBegin").split("T")[0];
		info.PubEnd = Util.getJsonString(obj, "PubEnd").split("T")[0];
		info.SellingStatus = Util.getJsonString(obj, "SellingStatus");
		info.SettleDay = Util.getJsonString(obj, "SettleDay").split("T")[0];
		info.TotalNumber = Util.getJsonString(obj, "TotalNumber");
		info.Unit = Util.getJsonString(obj, "Unit");
		
		info.ValueDay = Util.getJsonString(obj, "ValueDay").split("T")[0];
		info.Yield = Util.getJsonString(obj, "Yield");
		info.Id = Util.getJsonString(obj, "Id");
		info.Now = Util.getJsonString(obj, "Now").split("T")[0];
		info.ExtraYield = Util.getJsonString(obj, "ExtraYield");
		
		info.begin = (Integer.parseInt(info.MinNumber) * Integer.parseInt(info.Unit)) + "";
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

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

}
