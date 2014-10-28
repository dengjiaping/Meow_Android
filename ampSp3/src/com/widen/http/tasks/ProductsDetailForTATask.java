package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.ProductionsInfo2;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ProductsDetailForTATask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/ProductInfo/TA";
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
		return String.format("productNo=%s",strs[0]);
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
		
		info.businessLicense = Util.getJsonString(obj, "BusinessLicense");
	        
		info.startSellTime = Util.getJsonString(obj, "StartSellTime"); 
	      
		info.SettleDate = Util.getJsonString(obj,"SettleDate");
		
		info.availableShareCount = Util.getJsonString(obj, "AvailableShareCount");
        
		info.billNo = Util.getJsonString(obj, "BillNo");
		
		info.currentValueDate = Util.getJsonString(obj, "CurrentValueDate");
		
		info.drawee = Util.getJsonString(obj, "Drawee");
		
		info.draweeInfo = Util.getJsonString(obj, "DraweeInfo");
		
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
		
		info.consignmentAgreementName = Util.getJsonString(obj, "ConsignmentAgreementName");
		
		info.pledgeAgreementName = Util.getJsonString(obj, "PledgeAgreementName");
	        
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
		
		info.securedParty = Util.getJsonString(obj, "Securedparty");
		
		info.securedParty = Util.getJsonString(obj, "SecuredpartyInfo");
		
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
		
		info.guaranteeMode = Util.getJsonInt(obj, "GuaranteeMode");
   
        
		return info;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
