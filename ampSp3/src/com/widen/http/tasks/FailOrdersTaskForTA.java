package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.OrderInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class FailOrdersTaskForTA extends IHttpTask{

	
	
	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "/Orders/BA/Failed";
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
		
		BaseListInfo<OrderInfo> infos = new BaseListInfo<OrderInfo>();
		infos.putExtraData("PageIndex", Util.getJsonString(obj, "PageIndex"));
		infos.putExtraData("PageSize", Util.getJsonString(obj, "PageSize"));
		infos.putExtraData("TotalCount", Util.getJsonString(obj, "TotalCount"));
		infos.putExtraData("TotalPageCount", Util.getJsonString(obj, "TotalPageCount"));
		infos.putExtraData("HasNextPage", Util.getJsonBoolean(obj, "HasNextPage"));
		
		infos.hasMore =  Util.getJsonBoolean(obj, "HasNextPage");
		
		JSONArray items = obj.getJSONArray("Orders");
		
		for(int i=0;i<items.length();i++){
			OrderInfo info = new OrderInfo();
			JSONObject object = items.getJSONObject(i);

			
			info.OrderIdentifier = Util.getJsonString(object, "OrderIdentifier");
			info.OrderTime = Util.getJsonString(object, "OrderTime");
			info.Principal = Util.getJsonString(object, "Principal");
			info.OrderNo = Util.getJsonString(object, "OrderNo");
			info.failedReason = Util.getJsonString(object, "Message");
			
			
			info.Interest = Util.getJsonString(object, "Interest");
			info.UseableItemCount = Util.getJsonString(object, "UseableItemCount");
			info.ShowingStatus = Util.getJsonInt(object, "ShowingStatus");
		    	
			
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
