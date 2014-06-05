package com.widen.http.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.application.MyApplication;
import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.ItemInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class ItemsUseableTask implements IHttpTask{

	
	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/Items/Useable";
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
		return String.format("OrderId=%s&PageIndex=%s&PageSize=20",strs[0],strs[1]);
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
		BaseListInfo<ItemInfo> infos = new BaseListInfo<ItemInfo>();
		infos.putExtraData("HasNextPage", Util.getJsonBoolean(obj, "HasNextPage"));
		infos.putExtraData("PageIndex", Util.getJsonString(obj, "PageIndex"));
		infos.putExtraData("PageSize", Util.getJsonString(obj, "PageSize"));
		infos.putExtraData("TotalCount", Util.getJsonString(obj, "TotalCount"));
		infos.putExtraData("TotalPageCount", Util.getJsonString(obj, "TotalPageCount"));
		
		JSONArray items = obj.getJSONArray("Items");
		
		for(int i=0;i<items.length();i++){
			ItemInfo info = new ItemInfo();
			JSONObject object = items.getJSONObject(i);
			
			info.Description = Util.getJsonString(object, "Description");
			info.HasExpired = Util.getJsonBoolean(object, "HasExpired");
			info.Expires = Util.getJsonString(object, "Expires").split("T")[0];
			
			if(MyApplication.appContext.dm.widthPixels < 720){
				info.ImageSrc = Util.getJsonString(object, "ImageSrc").replace("{size}", "480");
			}else{
				info.ImageSrc = Util.getJsonString(object, "ImageSrc").replace("{size}", "720");
			}
			
			
			
			info.Title = Util.getJsonString(object, "Title");
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
