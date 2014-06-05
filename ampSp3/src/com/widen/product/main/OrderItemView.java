package com.widen.product.main;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.widen.R;
import com.widen.http.info.OrderInfo;
@EViewGroup(R.layout.order_item)
public class OrderItemView extends LinearLayout{

	@ViewById
	TextView OrderIdentifier;
	
	@ViewById
	Button use;
	
	@ViewById
	TextView used;
	
	@ViewById
	TextView Principal;
	
	@ViewById
	TextView Interest;
	
	@ViewById
	TextView OrderTime;
	
	
	public OrderItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OrderItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void bind(final OrderInfo info ,final Context context) {  
		OrderIdentifier.setText(info.OrderIdentifier);
		if(info.UseableItemCount.equals("-1")){
			used.setVisibility(View.VISIBLE);
			use.setVisibility(View.GONE);
		}else if(info.UseableItemCount.equals("0")){
			used.setVisibility(View.GONE);
			use.setVisibility(View.GONE);
		}else{
			used.setVisibility(View.GONE);
			use.setVisibility(View.VISIBLE);
		}
		
		Principal.setText(info.Principal + "元");
		Interest.setText(info.Interest + "元");
		OrderTime.setText(info.OrderTime.replace("T", " "));
		
		used.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(context, MyItemAct_.class);
				intent.putExtra("orderId", info.Id);
				intent.putExtra("flag", OrderListAct.FLAG);
				context.startActivity(intent);
			}
		});
		
	}

}
