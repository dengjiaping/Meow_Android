package com.widen.product.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.widen.R;
import com.widen.http.info.TimeLineInfo;
import com.widen.util.Util;
@EViewGroup(R.layout.time_line_item)
public class TimeLineItemView extends LinearLayout {

	@ViewById
	FrameLayout year_lay;
	
	@ViewById
	ImageView navi_arrow;
	
	@ViewById
	TextView year;
	
	@ViewById
	FrameLayout month_lay;
	
	@ViewById
	LinearLayout day_lay;
	
	@ViewById
	FrameLayout finall_lay;

	
	@ViewById
	TextView type_40_txt;
	
	@ViewById
	TextView month;
	
	@ViewById
	TextView day;
	
	@ViewById
	ImageView type_icon;
	
	@ViewById
	TextView txt_1;
	
	@ViewById
	TextView txt_2;
	
	@ViewById
	TextView name;
	
	@ViewById
	   ImageView timeline_color_line;
	
	@ViewById
	TextView today_txt;
	
	public TimeLineItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	public TimeLineItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void bind(TimeLineInfo info) throws Exception{ 
		
		year_lay.setVisibility(View.VISIBLE);	
		month_lay.setVisibility(View.VISIBLE);
		finall_lay.setVisibility(View.GONE);
		
		if(!info.Type.equals("40")){
			
		
			
		
		if(info.isYearHead){
			year_lay.setVisibility(View.VISIBLE);
		}else{
			year_lay.setVisibility(View.GONE);
		}
		
		if(info.isDayHead){
			day_lay.setVisibility(View.VISIBLE);
		}else{
			day_lay.setVisibility(View.GONE);
		}
		
		if(info.Type.equals("20")){			
			today_txt.setVisibility(View.VISIBLE);
		}else{			
			today_txt.setVisibility(View.GONE);
		}	

		if(info.Type.equals("20") && !info.isDayHead){
			day_lay.setVisibility(View.VISIBLE);
			today_txt.setVisibility(View.VISIBLE);
			month.setVisibility(View.GONE);
			day.setVisibility(View.GONE);
		}
		
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String str=info.Time;
		//Date now = sdf.parse("2014-04-24");				
		Date date =sdf.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		year.setText(calendar.get(Calendar.YEAR) + "");
		month.setText(Util.time.get(calendar.get(Calendar.MONTH)));
		
		if((calendar.get(Calendar.DAY_OF_MONTH) + "").length() == 1){
			day.setText("0" + calendar.get(Calendar.DAY_OF_MONTH));		
		}else{
			day.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");		
			
		}
		
		switch (Integer.parseInt(info.Type)) {
		case 0:
			name.setTextColor(getResources().getColor(R.color.yellow));
			name.setText("新的起点");
			timeline_color_line.setImageResource(R.drawable.timeline_yellow_line);
			txt_1.setText("您加入了金银猫");
			txt_2.setText("理财之旅就此开始");
			type_icon.setImageResource(R.drawable.type_0_icon);
			navi_arrow.setVisibility(View.GONE);
			break;
			
		case 10:
			
			name.setTextColor(getResources().getColor(R.color.red));
			name.setText("购买产品");
			timeline_color_line.setImageResource(R.drawable.timeline_red_line);
			type_icon.setImageResource(R.drawable.type_10_icon);
			
			txt_1.setText("订单金额 " + info.Principal + "元");
			txt_2.setText("预期收益 " + info.Interest + "元");
			navi_arrow.setVisibility(View.VISIBLE);
			break;
			
		case 20:
			name.setTextColor(getResources().getColor(R.color.yellow));
			name.setText("今日播报");
			timeline_color_line.setImageResource(R.drawable.timeline_yellow_line);
			if(Util.isEmpty(info.Principal)){
				txt_1.setText("您还没有做过任何投资哦");
				txt_2.setText("快去看看我们给力的产品吧!");
			}else{
				txt_1.setText("总在投资金 " + info.Principal + "元");
				txt_2.setText("预期收益 " + info.Interest + "元");
			}
			type_icon.setImageResource(R.drawable.type_20_icon);
			navi_arrow.setVisibility(View.GONE);
			break;	
			
		case 30:		
			if(info.IsPast){
				name.setTextColor(getResources().getColor(R.color.grey));
				name.setText("项目结束");
				timeline_color_line.setImageResource(R.drawable.timeline_grey_line);
				txt_1.setText("已结息 " + info.Interest + "元");
				txt_2.setText("归还本金 " + info.Principal + "元");
				type_icon.setImageResource(R.drawable.type_30_icon);	
			}else{
				if(info.Status.equals("50")){
					name.setTextColor(getResources().getColor(R.color.grey));
					name.setText("提前还款");
					timeline_color_line.setImageResource(R.drawable.timeline_grey_line);
					txt_1.setText("已结息 " + info.Interest + "元");
					txt_2.setText("归还本金 " + info.Principal + "元");
					
					type_icon.setImageResource(R.drawable.type_30_icon);
				}else {
					name.setTextColor(getResources().getColor(R.color.blue));
					name.setText("最迟还款日");
					timeline_color_line.setImageResource(R.drawable.timeline_blue_line);
					txt_1.setText("将结息 " + info.Interest + "元");
					txt_2.setText("归还本金 " + info.Principal + "元");
					type_icon.setImageResource(R.drawable.type_31_icon);
				}
				
				
			}
			navi_arrow.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		
		}else{
			year_lay.setVisibility(View.GONE);	
			month_lay.setVisibility(View.GONE);
			finall_lay.setVisibility(View.VISIBLE);
			if(!TextUtils.isEmpty(info.Identifier)){
				type_40_txt.setText(info.Identifier);
			}
		}
		
		
		
	}


	
}
