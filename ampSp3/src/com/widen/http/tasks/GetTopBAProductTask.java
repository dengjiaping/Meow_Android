package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.ProductionsInfo2;
import com.widen.http.model.SignInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class GetTopBAProductTask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/ProductInfo/BA/TOP";
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
		
		ProductionsInfo2 info = new ProductionsInfo2();		
		
		info.availableShareCount = Util.getJsonString(obj, "AvailableShareCount");
        info.bankName = Util.getJsonString(obj,"BankName");
		info.billNo = Util.getJsonString(obj, "BillNo");
		info.businessLicense = Util.getJsonString(obj, "BusinessLicense");
		info.currentValueDate = Util.getJsonString(obj, "CurrentValueDate");
		info.endorseImageLink = Util.getJsonString(obj, "EndorseImageLink");
		info.endorseImageThumbnailLink = Util.getJsonString(obj, "EndorseImageThumbnailLink");
		info.endSellTime = Util.getJsonString(obj, "EndSellTime");
		info.enterpriseName = Util.getJsonString(obj, "EnterpriseName");
		info.financingSum = Util.getJsonInt(obj, "FinancingSum");
		info.financingSumCount = Util.getJsonInt(obj, "FinancingSumCount");
		info.launchTime = Util.getJsonString(obj, "LaunchTime");
		info.maxShareCount = Util.getJsonInt(obj, "MaxShareCount");
		info.minShareCount = Util.getJsonInt(obj, "MinShareCount");
		info.onPreSale = Util.getJsonBoolean(obj, "OnPreSale");
		info.onSale = Util.getJsonBoolean(obj, "OnSale");
		info.paidShareCount = Util.getJsonInt(obj, "PaidShareCount");
		info.payingShareCount = Util.getJsonInt(obj, "PayingShareCount");
		info.period = Util.getJsonInt(obj, "Period");
		info.preEndSellTime = Util.getJsonString(obj, "PreEndSellTime");
		info.preSale = Util.getJsonBoolean(obj, "PreSale");
		info.preStartSellTime = Util.getJsonString(obj, "PreStartSellTime");
		info.productIdentifier = Util.getJsonString(obj, "ProductIdentifier");
		info.productName = Util.getJsonString(obj, "ProductName");
		info.productNo = Util.getJsonString(obj, "ProductNo");
		info.productNumber = Util.getJsonInt(obj, "ProductNumber");
		info.repaid = Util.getJsonBoolean(obj, "Repaid");
		info.repaymentDeadline = Util.getJsonString(obj, "RepaymentDeadline");
		info.serverTime = Util.getJsonString(obj, "ServerTime");
		info.showingStatus = Util.getJsonInt(obj, "ShowingStatus");
		info.soldOut = Util.getJsonBoolean(obj, "SoldOut");
		info.soldOutTime = Util.getJsonString(obj, "SoldOutTime");
		info.startSellTime = Util.getJsonString(obj, "StartSellTime");
		info.sumShareCount = Util.getJsonInt(obj, "SumShareCount");
		info.unitPrice = Util.getJsonInt(obj, "UnitPrice");
		info.usage = Util.getJsonString(obj, "Usage");
		info.valueDate = Util.getJsonString(obj, "ValueDate");
		info.valueDateMode = Util.getJsonInt(obj, "ValueDateMode");
		info.valueDateString = Util.getJsonString(obj, "ValueDateString");
		info.yield = Util.getJsonFloat(obj, "Yield");
		info.PaidPercent = Util.getJsonInt(obj, "PaidPercent");
		info.ExtraYield = Util.getJsonFloat(obj, "ExtraYield");
		info.SettleDate =Util.getJsonString(obj, "SettleDate");
		return info;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
