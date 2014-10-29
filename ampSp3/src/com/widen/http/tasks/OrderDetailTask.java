package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.OrderInfo;
import com.widen.http.model.ProductionsInfo2;
import com.widen.util.Constant;
import com.widen.util.Util;

public class OrderDetailTask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/Orders";
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
		return String.format("orderIdentifier=%s",strs[0]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		
		OrderInfo info = new OrderInfo();
		
		info.OrderTime = Util.getJsonString(obj, "OrderTime");
		info.Interest = Util.getJsonString(obj, "Interest");
		info.ExtraInterest = Util.getJsonString(obj, "ExtraInterest");
		info.ItemTitle = Util.getJsonString(obj, "ItemTitle");
		info.OrderIdentifier = Util.getJsonString(obj, "OrderIdentifier");
		info.Price = Util.getJsonString(obj, "Principal");
		info.UseableItemCount = Util.getJsonString(obj, "UseableItemCount");
		info.ShowingStatus = Util.getJsonInt(obj, "ShowingStatus");
		info.OrderNo = Util.getJsonString(obj, "OrderNo");
		
		ProductionsInfo2 productInfo = new ProductionsInfo2();
		
		info.prodcutDetailInfo = productInfo;
		
		productInfo.bankName = Util.getJsonString(obj, "BankName");
		
		productInfo.productIdentifier = Util.getJsonString(obj, "ProductNo");
		
		productInfo.yield = Util.getJsonFloat(obj, "Yield");
		   
		productInfo.period = Util.getJsonInt(obj, "Period");  
		
		productInfo.SettleDate = Util.getJsonString(obj, "SettleDate");
		
		productInfo.valueDate = Util.getJsonString(obj, "ValueDate");
		
		productInfo.repaymentDeadline = Util.getJsonString(obj, "RepaymentDeadline");
		
		productInfo.showingStatus = Util.getJsonInt(obj, "ShowingStatus");
		
		productInfo.pledgeAgreementName = Util.getJsonString(obj, "PledgeAgreementName");
		
		productInfo.consignmentAgreementName = Util.getJsonString(obj, "ConsignmentAgreementName");
		
		String tmpName = Util.getJsonString(obj, "ProductName");
		
		productInfo.productNumber = Util.getJsonInt(obj, "ProductNumber");
		
		productInfo.productName = String.format("%s第%d期", tmpName,productInfo.productNumber);
		
		return info;

	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
