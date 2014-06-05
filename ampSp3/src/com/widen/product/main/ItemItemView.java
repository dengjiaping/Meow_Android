package com.widen.product.main;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.widen.R;
import com.widen.application.MyApplication;
import com.widen.http.info.ItemInfo;
import com.widen.widget.URLImageView;
@EViewGroup(R.layout.item_item)
public class ItemItemView  extends LinearLayout{

	@ViewById
	URLImageView img;
	
	@ViewById
	Button use;
	
	@ViewById
	Button delete;
	
	@ViewById
	TextView name;
	
	@ViewById
	TextView InterestDescription;
	
	@ViewById
	TextView HasExpired;
	
	@ViewById
	TextView Expires;
	
	private Dialog dialog;
	public ItemItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ItemItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public void bind(ItemInfo info,final Context context ) {
		//img.setImageURL(info.ImageSrc);
		if(info.HasExpired){
			use.setVisibility(View.GONE);
			delete.setVisibility(View.VISIBLE);
			HasExpired.setTextColor(getResources().getColor(R.color.red));
			HasExpired.setText("已过期:");
		}else{
			use.setVisibility(View.VISIBLE);
			delete.setVisibility(View.GONE);
			HasExpired.setTextColor(getResources().getColor(R.color.grey));
			HasExpired.setText("有效期:");
			use.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.item_order_dialog);
		
					/*final TextView info_txt = (TextView) dialog
							.findViewById(R.id.info_txt);
		
					info_txt.setText("尊敬的  " + info.real_name + ",您的账户余额为");
		
					final TextView number_txt = (TextView) dialog
							.findViewById(R.id.number_txt);
					number_txt.setText(number + "元");
		
					final Button button = (Button) dialog.findViewById(R.id.finish);
		
					button.setOnClickListener(new OnClickListener() {
		
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							isShow = false;
						}
					});
					dialog.setCancelable(false);
					dialog.show();
					isShow = true;*/
				}
			});
		}
		
		name.setText(info.Title);
		InterestDescription.setText(info.Description);
		Expires.setText(info.Expires);
	}
	
}
