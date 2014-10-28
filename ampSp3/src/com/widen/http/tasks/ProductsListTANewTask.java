package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.ProductionsInfo2;

import com.widen.util.Constant;
import com.widen.util.Util;

public class ProductsListTANewTask extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/ProductInfo/TA/Page";
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
		return String.format("number=%s",strs[0]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		BaseListInfo<ProductionsInfo2> infos = new BaseListInfo<ProductionsInfo2>();
		
		infos.hasMore = Util.getJsonBoolean(obj, "HasNextPage");
		
		JSONArray jsonArray = obj.getJSONArray("Products");
		for(int i=0;i<jsonArray.length();i++){
			ProductionsInfo2 info = new ProductionsInfo2();
			JSONObject childJsonObject = jsonArray.getJSONObject(i);
			
			info.drawee = Util.getJsonString(childJsonObject, "Drawee");
			info.draweeInfo = Util.getJsonString(childJsonObject, "DraweeInfo");
			info.enterpriseInfo = Util.getJsonString(childJsonObject, "EnterpriseInfo");
			info.enterpriseLicense = Util.getJsonString(childJsonObject, "EnterpriseLicense");
			info.pledgeAgreementName = Util.getJsonString(childJsonObject, "PledgeAgreementName");
			info.securedParty = Util.getJsonString(childJsonObject, "Securedparty");
			info.securedPartyInfo = Util.getJsonString(childJsonObject, "SecuredpartyInfo");
			info.guaranteeMode = Util.getJsonInt(childJsonObject, "GuaranteeMode");
		       
		    info.consignmentAgreementName = Util.getJsonString(childJsonObject, "ConsignmentAgreementName");
		    
			info.availableShareCount = Util.getJsonString(childJsonObject, "AvailableShareCount");
			info.billNo = Util.getJsonString(childJsonObject, "BillNo");
			info.businessLicense = Util.getJsonString(childJsonObject, "BusinessLicense");
			info.currentValueDate = Util.getJsonString(childJsonObject, "CurrentValueDate");
			info.endorseImageLink = Util.getJsonString(childJsonObject, "EndorseImageLink");
			info.endorseImageThumbnailLink = Util.getJsonString(childJsonObject, "EndorseImageThumbnailLink");
			info.endSellTime = Util.getJsonString(childJsonObject, "EndSellTime");
			info.enterpriseName = Util.getJsonString(childJsonObject, "EnterpriseName");
			info.financingSum = Util.getJsonInt(childJsonObject, "FinancingSum");
			info.financingSumCount = Util.getJsonInt(childJsonObject, "FinancingSumCount");
			info.launchTime = Util.getJsonString(childJsonObject, "LaunchTime");
			info.maxShareCount = Util.getJsonInt(childJsonObject, "MaxShareCount");
			info.minShareCount = Util.getJsonInt(childJsonObject, "MinShareCount");
			info.onPreSale = Util.getJsonBoolean(childJsonObject, "OnPreSale");
			info.onSale = Util.getJsonBoolean(childJsonObject, "OnSale");
			info.paidShareCount = Util.getJsonInt(childJsonObject, "PaidShareCount");
			info.payingShareCount = Util.getJsonInt(childJsonObject, "PayingShareCount");
			info.period = Util.getJsonInt(childJsonObject, "Period");
			info.preEndSellTime = Util.getJsonString(childJsonObject, "PreEndSellTime");
			info.preSale = Util.getJsonBoolean(childJsonObject, "PreSale");
			info.preStartSellTime = Util.getJsonString(childJsonObject, "PreStartSellTime");
			info.productIdentifier = Util.getJsonString(childJsonObject, "ProductIdentifier");
			info.productName = Util.getJsonString(childJsonObject, "ProductName");
			info.productNo = Util.getJsonString(childJsonObject, "ProductNo");
			info.productNumber = Util.getJsonInt(childJsonObject, "ProductNumber");
			info.repaid = Util.getJsonBoolean(childJsonObject, "Repaid");
			info.repaymentDeadline = Util.getJsonString(childJsonObject, "RepaymentDeadline");
			info.serverTime = Util.getJsonString(childJsonObject, "ServerTime");
			info.showingStatus = Util.getJsonInt(childJsonObject, "ShowingStatus");
			info.soldOut = Util.getJsonBoolean(childJsonObject, "SoldOut");
			info.soldOutTime = Util.getJsonString(childJsonObject, "SoldOutTime");
			info.startSellTime = Util.getJsonString(childJsonObject, "StartSellTime");
			info.sumShareCount = Util.getJsonInt(childJsonObject, "SumShareCount");
			info.unitPrice = Util.getJsonInt(childJsonObject, "UnitPrice");
			info.usage = Util.getJsonString(childJsonObject, "Usage");
			info.valueDate = Util.getJsonString(childJsonObject, "ValueDate");
			info.valueDateMode = Util.getJsonInt(childJsonObject, "ValueDateMode");
			info.valueDateString = Util.getJsonString(childJsonObject, "ValueDateString");
			info.yield = Util.getJsonFloat(childJsonObject, "Yield");
			info.PaidPercent = Util.getJsonInt(childJsonObject, "PaidPercent");
			info.ExtraYield = Util.getJsonFloat(childJsonObject, "ExtraYield");
			    

			infos.add(info);
		}
		
		
		
		
		return infos;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
