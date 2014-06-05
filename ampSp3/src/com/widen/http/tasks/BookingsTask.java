package com.widen.http.tasks;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.widen.http.IHttpTask;
import com.widen.http.info.BaseListInfo;
import com.widen.http.info.BookInfo;
import com.widen.util.Constant;
import com.widen.util.Util;

public class BookingsTask implements IHttpTask{

	private String[] strs;
	@Override
	public String getSubUrl() {
		// TODO Auto-generated method stub
		return "amp/api/v1/bookings.json";
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
		return String.format("start=%s&num=20",strs[0]);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Constant.GET;
	}

	@Override
	public Object getData(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		
		BaseListInfo<BookInfo> list = new BaseListInfo<BookInfo>();
		
		JSONArray records = obj.getJSONArray("records");
		for(int i =0 ; i < records.length() ; i++){
			BookInfo info = new BookInfo();
			JSONObject childObject = records.getJSONObject(i);			
			info.production_name = Util.getJsonString(childObject, "production_name");
			
			info.booking_num = Util.getJsonString(childObject, "booking_num");
			info.id = Util.getJsonString(childObject, "id");
			info.server_time = Util.getJsonString(childObject, "server_time");
			info.status = Util.getJsonString(childObject, "status");
			info.production_no = Util.getJsonString(childObject, "production_no");
			info.expected_yield = Util.getJsonString(childObject, "expected_yield");
			info.period = Util.getJsonString(childObject, "period");
			
			String booking_at = Util.getJsonString(childObject, "booking_at");
			try {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf1.parse(booking_at);
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
				info.booking_at = sdf2.format(date);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			info.project_ending_time = Util.getJsonString(childObject, "project_ending_time");
			info.bank_logo = Util.getJsonString(childObject, "bank_logo");
			info.benefit_begin = Util.getJsonString(childObject, "benefit_begin");
			list.add(info);
		}
		
		return list;
	}

	@Override
	public Object getData(JSONArray obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
