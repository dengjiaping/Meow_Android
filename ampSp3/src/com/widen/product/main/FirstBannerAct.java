package com.widen.product.main;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.widen.R;
import com.widen.product.BaseActivity;
import com.widen.widget.PointsView;

@EActivity(R.layout.first_banner)
public class FirstBannerAct extends BaseActivity{

	@ViewById 
	ViewPager viewpager;
	ArrayList<View> list;
	
	@ViewById
	PointsView point;
	
	@AfterViews
	public void afterViews() {
		setView();
	}
	
	private void setView(){
		list = new ArrayList<View>();
		LayoutInflater lf = getLayoutInflater().from(this);
		View view1 = lf.inflate(R.layout.first_banner_lay_1, null);
		View view2 = lf.inflate(R.layout.first_banner_lay_2, null);
		View view3 = lf.inflate(R.layout.first_banner_lay_3, null);
		view3.findViewById(R.id.begin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FirstBannerAct.this,MainAct_.class));
				finish();
			}
		});
		list.add(view1);
		list.add(view2);
		list.add(view3);
		viewpager.setAdapter(new MyAdapt());
		point.setCount(list.size());
		
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				point.setIndex(arg0);				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub					
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub		
			}
		});
		
	}
	
	
	private class MyAdapt extends PagerAdapter{

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(list.get(position));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("第一次启动页");
		MobclickAgent.onResume(this);		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("第一次启动页");
		MobclickAgent.onPause(this);
	}
	
}
