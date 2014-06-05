package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.OrderInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class OrderInfoTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/TimeLine/OrderInfo";
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
		return String.format("orderNo=%s",strs[0]);
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
		
		JSONObject Order = obj.getJSONObject("Order");
		JSONObject Product = obj.getJSONObject("Product");
		OrderInfo info = new OrderInfo();
		info.CreatedAt = Util.getJsonString(Order, "CreatedAt").replace("T", " ");
		info.ExpectedPrice = Util.getJsonString(Order, "ExpectedPrice");
		info.ExtraInterest = Util.getJsonString(Order, "ExtraInterest");
		info.ItemTitle = Util.getJsonString(Order, "ItemTitle");
		info.OrderId = Util.getJsonString(Order, "OrderId");
		info.OrderNo = Util.getJsonString(Order, "OrderNo");
		info.Price = Util.getJsonString(Order, "Price");
		info.UseableItemCount = Util.getJsonString(Order, "UseableItemCount");
		
		
		info.BankName = Util.getJsonString(Product, "BankName");
		info.DueDate = Util.getJsonString(Product, "DueDate").split("T")[0];
		info.Duration = Util.getJsonString(Product, "Duration");
		info.ExtraYield = Util.getJsonString(Product, "ExtraYield");
		info.FundedPercentage = Util.getJsonString(Product, "FundedPercentage");
		info.IsRecommand = Util.getJsonString(Product, "IsRecommand");
		info.MaxNumber = Util.getJsonString(Product, "MaxNumber");
		info.MinNumber = Util.getJsonString(Product, "MinNumber");
		info.Name = Util.getJsonString(Product, "Name");
		info.ProductIdentifier = Util.getJsonString(Product, "ProductIdentifier");

		info.PubBegin = Util.getJsonString(Product, "PubBegin");
		info.PubEnd = Util.getJsonString(Product, "PubEnd");
		info.SellingStatus = Util.getJsonString(Product, "SellingStatus");
		
		info.SettleDay = Util.getJsonString(Product, "SettleDay").split("T")[0];
		info.TotalNumber = Util.getJsonString(Product, "TotalNumber");
		info.Unit = Util.getJsonString(Product, "Unit");
		info.ValueDay = Util.getJsonString(Product, "ValueDay");
		info.Yield = Util.getJsonString(Product, "Yield");
		
		info.Id = Util.getJsonString(Product, "Id");
		
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
