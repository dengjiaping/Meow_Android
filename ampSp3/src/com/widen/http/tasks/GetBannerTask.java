package com.widen.http.tasks;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.model.BannerInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class GetBannerTask  extends IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "https://cmsadmin.jinyinmao.com.cn/index.php?m=dbsource&c=call&a=get&id=2";
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
		
		List<BannerInfo> bannerList = new LinkedList<BannerInfo>();
		
		JSONArray jarray = obj.getJSONArray("");
		
		for(int i = 0;i < jarray.length();i++){
		
			JSONObject jobject = jarray.getJSONObject(i);
			
		    String jsonstr = Util.getJsonString(jobject , "setting");
		    
		    jsonstr = jsonstr.replaceAll("\'","\"");
		    
		    try{
		    	
		    	BannerInfo info = new BannerInfo();
		    	
		    	JSONObject newJObject = new JSONObject(jsonstr);
		    	info.Src_480 = info.Src_640 =info.Src_720 = Util.getJsonString(newJObject,"imageurl");
		    	info.Type = Util.getJsonString(newJObject,"alt");
		    	info.Url = Util.getJsonString(newJObject,"linkurl");
		    
		    	bannerList.add(info);
		    	
		    }catch(Exception e){
		    	return null;
		    }
		    
		    
		}
		return null;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRalativeUrl(){
		return false;
	}
	

}
