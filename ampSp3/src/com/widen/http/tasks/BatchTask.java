package com.widen.http.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.widen.http.IHttpTask;
import com.widen.http.info.BannerInfo;
import com.widen.http.info.TopInfo;
import com.widen.http.info.TopJsonInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class BatchTask implements IHttpTask{

	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/$batch";
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return getSubUrl() + "_" + getType();
	}

	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		
		Gson gson = new Gson();
		TopJsonInfo info1 = new TopJsonInfo();
		info1.setMethod("GET");
		info1.setRelativeUrl("Api/V1/Meow/Configurations/AppBanners");
		
		TopJsonInfo info2 = new TopJsonInfo();
		info2.setMethod("GET");
		info2.setRelativeUrl("Api/V1/Amp/Products?top=1");
		
		
		TopJsonInfo info3 = new TopJsonInfo();
		info3.setMethod("GET");
		info3.setRelativeUrl("Api/V1/Statistic/Investment/Summary");
		
		List<TopJsonInfo> list = new ArrayList<TopJsonInfo>();  
		list.add(info1);
		list.add(info2);
		list.add(info3);
		
		String string = gson.toJson(list);	
		
		return string;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.POST;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub	
		return null;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		
		
		TopInfo info = new TopInfo();
		JSONObject object_1 = (JSONObject) obj.get(0);
		String string = Util.getJsonString(object_1, "Body");
		JSONObject object = new JSONObject(string);

		//JSONObject object1_body = object_1.getJSONObject("Body");
		JSONObject object1_body = new JSONObject(string);

		JSONArray banners = object1_body.getJSONArray("Banners");

		String root = Util.getJsonString(object1_body, "Root");

		JSONObject size = object1_body.getJSONObject("Size");


		String jpg_480 = root + size.getString("480") + "/";
		String jpg_640 = root + size.getString("640") + "/";
		String jpg_720 = root + size.getString("720") + "/";
		

		
		
		for(int i=0;i<banners.length();i++){
			JSONObject bannnerObject = (JSONObject) banners.get(i);
			BannerInfo bannerInfo = new BannerInfo();
			bannerInfo.Type = Util.getJsonString(bannnerObject, "Type");
			bannerInfo.Url = Util.getJsonString(bannnerObject, "Url");		
			
			bannerInfo.Src_480  = Util.toJpgUrl(jpg_480 + bannnerObject.getString("Src"));
			bannerInfo.Src_640  = Util.toJpgUrl(jpg_640 + bannnerObject.getString("Src"));
			bannerInfo.Src_720  = Util.toJpgUrl(jpg_720 + bannnerObject.getString("Src"));
			info.bannerInfos.add(bannerInfo);
			
		}

		
		   JSONObject object2 = (JSONObject) obj.get(1);
	
		
		//JSONObject object2_body = object2.getJSONObject("Body");
		String string2 = Util.getJsonString(object2, "Body");
		JSONObject object2_body = new JSONObject(string2);

		JSONArray items = object2_body.getJSONArray("Items");
		
		JSONObject itemInfo = (JSONObject) items.get(0);
		
		info.topInfo2.BankName = Util.getJsonString(itemInfo, "BankName");
		info.topInfo2.Duration = Util.getJsonString(itemInfo, "Duration");
		info.topInfo2.MinNumber = Util.getJsonString(itemInfo, "MinNumber");
		info.topInfo2.Name = Util.getJsonString(itemInfo, "Name");
		info.topInfo2.ProductIdentifier =Util.getJsonString(itemInfo, "ProductIdentifier");
		info.topInfo2.TotalNumber = Util.getJsonString(itemInfo, "TotalNumber");
		info.topInfo2.Unit =Util.getJsonString(itemInfo, "Unit");
		info.topInfo2.Yield = Util.getJsonString(itemInfo, "Yield");
		
		info.topInfo2.Id = Util.getJsonString(itemInfo, "Id");
		
		
		 JSONObject object3 = (JSONObject) obj.get(2);
	 
		 String string3 = Util.getJsonString(object3, "Body");
 
		 
		 info.code = Util.getJsonString(object3, "Code");
		 if(info.code.equals("200")){
			 JSONObject object3_body = new JSONObject(string3);		
			 info.AccruedEarnings = Util.getJsonString(object3_body, "AccruedEarnings"); 
			 info.AppEearningSpeed = Util.getJsonString(object3_body, "AppEearningSpeed");
			 info.DefeatedPercent = Util.getJsonString(object3_body, "DefeatedPercent");
			 info.EarningsAgain = Util.getJsonString(object3_body, "EarningsAgain");
			 info.HasOrders = Util.getJsonBoolean(object3_body, "HasOrders");
			 info.HasShown = Util.getJsonBoolean(object3_body, "HasShown");		 
			 info.InterestPerSecond = Util.getJsonString(object3_body, "InterestPerSecond");
			 
			 info.EarningsAgainDuration = Util.getJsonString(object3_body, "EarningsAgainDuration");
		 }		 		
		
		 
		return info;
	}

	@Override
	public boolean isNewApi() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/json; charset=utf-8";
	}

}
