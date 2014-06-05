package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ProductionsInfo2;
import com.widen.product.BaseFragmentAct;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ProductsListNewTask implements IHttpTask{

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
		return String.format("PageIndex=%s&PageSize=20",strs[0]);
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
		
		JSONArray jsonArray = obj.getJSONArray("Items");
		for(int i=0;i<jsonArray.length();i++){
			ProductionsInfo2 info = new ProductionsInfo2();
			JSONObject childJsonObject = jsonArray.getJSONObject(i);
			info.BankName = Util.getJsonString(childJsonObject, "BankName");
			info.DueDate = Util.getJsonString(childJsonObject, "DueDate").split("T")[0];
			
			
			
			info.Duration = Util.getJsonString(childJsonObject, "Duration");
			info.FundedPercentage = Util.getJsonString(childJsonObject, "FundedPercentage");
			info.IsRecommand = Util.getJsonString(childJsonObject, "IsRecommand");
			info.MaxNumber = Util.getJsonString(childJsonObject, "MaxNumber");
			info.MinNumber = Util.getJsonString(childJsonObject, "MinNumber");
			info.Name = Util.getJsonString(childJsonObject, "Name");
			
			info.ProductIdentifier = Util.getJsonString(childJsonObject, "ProductIdentifier");
			info.PubBegin = Util.getJsonString(childJsonObject, "PubBegin").split("T")[0];
			info.PubEnd = Util.getJsonString(childJsonObject, "PubEnd").split("T")[0];
			info.SellingStatus = Util.getJsonString(childJsonObject, "SellingStatus");
			info.SettleDay = Util.getJsonString(childJsonObject, "SettleDay").split("T")[0];
			info.TotalNumber = Util.getJsonString(childJsonObject, "TotalNumber");
			info.Unit = Util.getJsonString(childJsonObject, "Unit");
			info.ValueDay = Util.getJsonString(childJsonObject, "ValueDay").split("T")[0];
			
			
			info.Yield = Util.getJsonString(childJsonObject, "Yield");
			info.Id = Util.getJsonString(childJsonObject, "Id");
			
			
			info.begin = (Integer.parseInt(info.MinNumber) * Integer.parseInt(info.Unit)) + "";

			infos.add(info);
		}
		
		
		
		
		return infos;
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
