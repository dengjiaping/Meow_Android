package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.AddressesInfo;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ProductionsInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class AddressesListTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "passport/api/v1/addresses/list.json";
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
		
		
		
		
		return null;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		BaseListInfo<AddressesInfo> list = new BaseListInfo<AddressesInfo>();
		for (int i = 0; i < obj.length(); i++) {
			JSONObject childObject = obj.getJSONObject(i);
			AddressesInfo info = new AddressesInfo();
			info.id = Util.getJsonString(childObject, "id");	
			info.consignee_name = Util.getJsonString(childObject, "consignee_name");	
			info.street_address = Util.getJsonString(childObject, "street_address");	
			info.region_id = Util.getJsonString(childObject, "region_id");	
			info.cellphone = Util.getJsonString(childObject, "cellphone");	
			info.is_default = Util.getJsonString(childObject, "is_default");	
			info.region_name = Util.getJsonString(childObject, "region_name");		
			list.add(info);
		}
		return list;
	}

	@Override
	public boolean isNewApi() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

}
