package com.widen.product.main;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.widen.R;
import com.widen.http.info.ProductionsInfo2;
 
@EViewGroup(R.layout.product_item)
public class ProductItemView extends LinearLayout{
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView Yield_txt;
	
	@ViewById
	TextView Duration;
	
	@ViewById
	TextView TotalNumber;
	
	@ViewById
	TextView being;
	
	@ViewById
	TextView Name;
	
	@ViewById
	ImageView SellingStatus;
	
	@ViewById
	ProgressBar product_progressbar;
	
	@ViewById
	TextView Date;
	
	@ViewById
	TextView BankName;
	
	@ViewById
	FrameLayout product_progressbar_lay;
	
	@ViewById
	TextView bank_txt;
	
	/*@ViewById
	ImageView recommand;
	
	@ViewById
	TextView Yield;
	
	@ViewById
	TextView Yield_txt;
	
	@ViewById
	TextView Duration;
	
	@ViewById
	TextView MinNumber;
	
	@ViewById
	TextView MaxNumber;
	
	@ViewById
	TextView Name;
	
	@ViewById
	TextView FundedPercentage;
	
	@ViewById
	TextView Date;
	
	@ViewById
	TextView BankName;
	
	@ViewById
	ImageView SellingStatus;
*/
	public ProductItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ProductItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	public void bind(ProductionsInfo2 info ) {  	
		Yield.setText(info.Yield);
		Duration.setText(info.Duration + "天");
		TotalNumber.setText(info.TotalNumber + "元");
		being.setText(info.begin + "元起投");
		
		Name.setText(info.Name);
		BankName.setText(info.BankName);
		
		
		Duration.setTextColor(getResources().getColor(R.color.black));
		Name.setTextColor(getResources().getColor(R.color.black));
		Date.setTextColor(getResources().getColor(R.color.grey));
		BankName.setTextColor(getResources().getColor(R.color.grey));
		
		TotalNumber.setTextColor(getResources().getColor(R.color.black));
		being.setTextColor(getResources().getColor(R.color.black));
		bank_txt.setTextColor(getResources().getColor(R.color.grey));
		
		switch (Integer.parseInt(info.SellingStatus)) {
		case 10:
			Yield.setTextColor(getResources().getColor(R.color.red));
			Yield_txt.setTextColor(getResources().getColor(R.color.red));			
			SellingStatus.setImageResource(R.drawable.wait_sale_icon);
			Date.setText("开售日期  " + info.PubBegin);
			product_progressbar_lay.setVisibility(View.GONE);
			break;
			
		case 20:
			Yield.setTextColor(getResources().getColor(R.color.red));
			Yield_txt.setTextColor(getResources().getColor(R.color.red));
			SellingStatus.setImageResource(R.drawable.rushbuy_icon);
			product_progressbar_lay.setVisibility(View.VISIBLE);		
			product_progressbar.setProgress(Integer.parseInt(info.FundedPercentage));
			Date.setText("停售日期  " + info.PubEnd);
			break;
			
		case 30:
			Yield.setTextColor(getResources().getColor(R.color.red));
			Yield_txt.setTextColor(getResources().getColor(R.color.red));
			SellingStatus.setImageResource(R.drawable.wait_carryinterest_icon);
			product_progressbar_lay.setVisibility(View.GONE);
			//Date.setText("起息日期  " + info.ValueDay);
			Date.setText("购买成功，即刻起息");
			break;
	
		case 40:
			Yield.setTextColor(getResources().getColor(R.color.red));
			Yield_txt.setTextColor(getResources().getColor(R.color.red));
			SellingStatus.setImageResource(R.drawable.wait_payback_icon);
			product_progressbar_lay.setVisibility(View.GONE);

			Date.setText("结息日期  " + info.SettleDay);
			break;
			
		case 50:
			Yield.setTextColor(getResources().getColor(R.color.red));
			Yield_txt.setTextColor(getResources().getColor(R.color.red));
			SellingStatus.setImageResource(R.drawable.paybacking_icon);
			product_progressbar_lay.setVisibility(View.GONE);
			Date.setText("最迟还款日  " + info.DueDate);
			break;	
			
		case 60:
			
			TotalNumber.setTextColor(getResources().getColor(R.color.grey_dip));
			being.setTextColor(getResources().getColor(R.color.grey_dip));
			bank_txt.setTextColor(getResources().getColor(R.color.grey_dip));
			
			SellingStatus.setImageResource(R.drawable.sale_over_icon);
			Yield_txt.setTextColor(getResources().getColor(R.color.grey_dip));
			Yield.setTextColor(getResources().getColor(R.color.grey_dip));
			Duration.setTextColor(getResources().getColor(R.color.grey_dip));			
			Name.setTextColor(getResources().getColor(R.color.grey_dip));
			Date.setTextColor(getResources().getColor(R.color.grey_dip));
			BankName.setTextColor(getResources().getColor(R.color.grey_dip));

			Date.setText("本息已全部归还");
			break;		

		default:
			break;
		}
	}
	
	
}
