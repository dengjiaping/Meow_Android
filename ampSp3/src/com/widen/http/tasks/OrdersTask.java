package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.OrderInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class OrdersTask implements IHttpTask{
	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Orders";
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
	public String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		
		BaseListInfo<OrderInfo> infos = new BaseListInfo<OrderInfo>();
		infos.putExtraData("PageIndex", Util.getJsonString(obj, "PageIndex"));
		infos.putExtraData("PageSize", Util.getJsonString(obj, "PageSize"));
		infos.putExtraData("TotalCount", Util.getJsonString(obj, "TotalCount"));
		infos.putExtraData("TotalPageCount", Util.getJsonString(obj, "TotalPageCount"));
		infos.putExtraData("HasNextPage", Util.getJsonBoolean(obj, "HasNextPage"));
		
		JSONArray items = obj.getJSONArray("Items");
		
		for(int i=0;i<items.length();i++){
			OrderInfo info = new OrderInfo();
			JSONObject object = items.getJSONObject(i);
			info.Interest = Util.getJsonString(object, "Interest");
			info.OrderIdentifier = Util.getJsonString(object, "OrderIdentifier");
			info.OrderTime = Util.getJsonString(object, "OrderTime");
			info.Principal = Util.getJsonString(object, "Principal");
			info.UseableItemCount = Util.getJsonString(object, "UseableItemCount");
			info.Id = Util.getJsonString(object, "Id");
			
			
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

}
