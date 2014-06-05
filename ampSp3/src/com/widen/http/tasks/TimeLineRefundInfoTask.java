package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.OrderInfo;
import com.widen.http.info.RefundInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class TimeLineRefundInfoTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/TimeLine/RefundInfo";
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
		return String.format("productIdentifier=%s",strs[0]);
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
		RefundInfo info = new RefundInfo();
		JSONObject Product = obj.getJSONObject("Product");
		info.BankName = Util.getJsonString(Product, "BankName");
		info.DueDate = Util.getJsonString(Product, "DueDate");
		info.Duration = Util.getJsonString(Product, "Duration");
		info.ExtraYield = Util.getJsonString(Product, "ExtraYield");
		info.FundedPercentage = Util.getJsonString(Product, "FundedPercentage");
		info.IsRecommand = Util.getJsonString(Product, "IsRecommand");
		info.MaxNumber = Util.getJsonString(Product, "MaxNumber");
		info.MinNumber = Util.getJsonString(Product, "MinNumber");
		info.Name = Util.getJsonString(Product, "Name");
		info.ProductIdentifier = Util.getJsonString(Product, "ProductIdentifier");
		info.PubBegin = Util.getJsonString(Product, "PubBegin").split("T")[0];
		info.PubEnd = Util.getJsonString(Product, "PubEnd").split("T")[0];
		info.SellingStatus = Util.getJsonString(Product, "SellingStatus");
		info.SettleDay = Util.getJsonString(Product, "SettleDay").split("T")[0];
		info.TotalNumber = Util.getJsonString(Product, "TotalNumber");
		info.Unit = Util.getJsonString(Product, "Unit");
		info.ValueDay = Util.getJsonString(Product, "ValueDay").split("T")[0];
		info.Yield = Util.getJsonString(Product, "Yield");
		info.Id = Util.getJsonString(Product, "Id");
		info.Now = Util.getJsonString(obj, "Now");
		JSONArray Orders = obj.getJSONArray("Orders");
		
		for(int i=0;i< Orders.length();i++){
			OrderInfo orderInfo = new OrderInfo();
			JSONObject object = Orders.getJSONObject(i);
			orderInfo.CreatedAt = Util.getJsonString(object, "CreatedAt");
			orderInfo.ExpectedPrice = Util.getJsonString(object, "ExpectedPrice");
			orderInfo.ExtraInterest = Util.getJsonString(object, "ExtraInterest");
			orderInfo.OrderNo = Util.getJsonString(object, "OrderNo");
			orderInfo.Price = Util.getJsonString(object, "Price");
			info.infos.add(orderInfo);
		}
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
