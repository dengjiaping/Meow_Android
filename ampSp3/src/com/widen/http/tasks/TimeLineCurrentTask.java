package com.widen.http.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.umeng.common.net.o;
import com.widen.application.MyApplication;
import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.TimeLineInfo;
import com.widen.product.main.TimeLineFragment;
import com.widen.product.main.TimeLineFragment.MyAdapter;
import com.widen.util.Constant;
import com.widen.util.Util;

public class TimeLineCurrentTask implements IHttpTask{
	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "Api/V1/TimeLine/Current";
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
		return String.format("count=%s",strs[0]);
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
	
		JSONArray array = obj.getJSONArray("Items");	
		
		BaseListInfo<TimeLineInfo> infos = new BaseListInfo<TimeLineInfo>();
		TimeLineFragment.timestamp = Util.getJsonString(obj, "Timestamp");	
		String yearhead = "";
		String dayhead = "";
		//String today = "2014-04-23";
		int start = 0;
		int end = 0;
		
		for(int i=0;i<array.length();i++){
			
			JSONObject object = array.getJSONObject(i);
			TimeLineInfo info = new TimeLineInfo();
			info.Identifier = Util.getJsonString(object, "Identifier");
			info.Interest = Util.getJsonString(object, "Interest");
			info.Principal = Util.getJsonString(object, "Principal");		
			
			info.Time = Util.getJsonString(object, "Time").split("T")[0];
			info.Type = Util.getJsonString(object, "Type");
			info.IsPast = Util.getJsonBoolean(object, "IsPast");
			info.Status = Util.getJsonString(object, "Status");
			if(info.Type.equals("20")){
				start = 0 - i;
				infos.putExtraData("start", start);
				TimeLineFragment.item_position = i;
			}
			
			if(i == array.length()-1){
				end = i + start;
				infos.putExtraData("end", end);
			}
			
			if(!info.Type.equals("40")){
						
				if(!yearhead.equals(info.Time.substring(0, 4))){				
					info.isYearHead = true;				
					yearhead = info.Time.substring(0, 4);				
				}
				
				if(!dayhead.equals(info.Time)){
					info.isDayHead = true;
					dayhead = info.Time;
				}			
				
	/*			if(MyApplication.appContext.serverTime.equals(info.Time)){
					info.isToday = true;
					infos.putExtraData("today", i);
				}*/
			
			}
			
			if(info.Type.equals("0")){
				infos.putExtraData("top","top");
			}else if((info.Type.equals("40"))){
				infos.putExtraData("bottom", "bottom");
			}	
			
			
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
