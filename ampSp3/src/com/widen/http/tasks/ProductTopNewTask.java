package com.widen.http.tasks;

import java.util.ArrayList;

import org.apache.http.params.HttpAbstractParamBean;
import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.HttpConfig;
import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ProductionsInfo;
import com.widen.http.info.ProductionsInfo2;
import com.widen.http.info.ProductionsTopInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ProductTopNewTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/App/IntergrationApi/TopProducts";
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
		return String.format("Top=%s",strs[0]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		
		ProductionsTopInfo info = new ProductionsTopInfo();
		JSONObject bannersInfo = obj.getJSONObject("Banners");
		JSONArray banners = bannersInfo.getJSONArray("Banners");
		String root = bannersInfo.getString("Root");
		JSONObject size = bannersInfo.getJSONObject("Size");
		
		String jpg_320 = root + size.getString("320") + "/";
		String jpg_480 = root + size.getString("480") + "/";
		String jpg_640 = root + size.getString("640") + "/";
		
		for(int i=0;i<banners.length();i++){
			String jpg_i = (String) banners.get(i);			
			info.banner_320.add(Util.toJpgUrl(jpg_320 + jpg_i));
			info.banner_480.add(Util.toJpgUrl(jpg_320 + jpg_i));
			info.banner_640.add(Util.toJpgUrl(jpg_320 + jpg_i));
			
		}
		
		
		JSONObject recommandedProducts = obj.getJSONObject("RecommandedProducts");
		
		JSONArray jsonArray = recommandedProducts.getJSONArray("Items");
		
			ProductionsInfo2 info2 = new ProductionsInfo2();
			JSONObject childJsonObject = jsonArray.getJSONObject(0);
			info2.Id = Util.getJsonString(childJsonObject, "Id");
			info2.BankName = Util.getJsonString(childJsonObject, "BankName");
			info2.Duration = Util.getJsonString(childJsonObject, "Duration");
			info2.MinNumber = Util.getJsonString(childJsonObject, "MinNumber");
			info2.Name = Util.getJsonString(childJsonObject, "Name");
			info2.TotalNumber = Util.getJsonString(childJsonObject, "TotalNumber");
			info2.Unit = Util.getJsonString(childJsonObject, "Unit");
			info2.Yield = Util.getJsonString(childJsonObject, "Yield");
			info2.ProductIdentifier = Util.getJsonString(childJsonObject, "ProductIdentifier");
			info.info2 = info2;
		
		
		return info;
		/*ArrayList<ProductionsInfo2> infos = new ArrayList<ProductionsInfo2>();
		JSONArray jsonArray = obj.getJSONArray("Items");
		for(int i=0;i<jsonArray.length();i++){
			ProductionsInfo2 info = new ProductionsInfo2();
			JSONObject childJsonObject = jsonArray.getJSONObject(i);
			info.Id = Util.JsonTool(childJsonObject, "Id");
			info.BankName = Util.JsonTool(childJsonObject, "BankName");
			info.Duration = Util.JsonTool(childJsonObject, "Duration");
			info.MinNumber = Util.JsonTool(childJsonObject, "MinNumber");
			info.Name = Util.JsonTool(childJsonObject, "Name");
			info.TotalNumber = Util.JsonTool(childJsonObject, "TotalNumber");
			info.Yield = Util.JsonTool(childJsonObject, "Yield");
			info.ProductIdentifier = Util.JsonTool(childJsonObject, "ProductIdentifier");
			infos.add(info);
		}
		return infos;*/
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
