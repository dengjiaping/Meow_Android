//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class HomeFragment_
    extends HomeFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.home, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static HomeFragment_.FragmentBuilder_ builder() {
        return new HomeFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        Yield = ((TextView) hasViews.findViewById(com.widen.R.id.Yield));
        txt_2 = ((TextView) hasViews.findViewById(com.widen.R.id.txt_2));
        progressbar = ((ProgressBar) hasViews.findViewById(com.widen.R.id.progressbar));
        horizontalScrollView = ((HorizontalScrollView) hasViews.findViewById(com.widen.R.id.horizontalScrollView));
        Unit = ((TextView) hasViews.findViewById(com.widen.R.id.Unit));
        no_login_lay = ((LinearLayout) hasViews.findViewById(com.widen.R.id.no_login_lay));
        logined_lay = ((FrameLayout) hasViews.findViewById(com.widen.R.id.logined_lay));
        Name = ((TextView) hasViews.findViewById(com.widen.R.id.Name));
        logined_lay2 = ((FrameLayout) hasViews.findViewById(com.widen.R.id.logined_lay2));
        txt_3 = ((TextView) hasViews.findViewById(com.widen.R.id.txt_3));
        swipeRefreshLayout = ((SwipeRefreshLayout) hasViews.findViewById(com.widen.R.id.swipeRefreshLayout));
        act_txt2 = ((TextView) hasViews.findViewById(com.widen.R.id.act_txt2));
        banners_lay = ((LinearLayout) hasViews.findViewById(com.widen.R.id.banners_lay));
        BankName = ((TextView) hasViews.findViewById(com.widen.R.id.BankName));
        Duration = ((TextView) hasViews.findViewById(com.widen.R.id.Duration));
        txt_1 = ((TextView) hasViews.findViewById(com.widen.R.id.txt_1));
        accrued_txt = ((TextView) hasViews.findViewById(com.widen.R.id.accrued_txt));
        cat_icon = ((ImageView) hasViews.findViewById(com.widen.R.id.cat_icon));
        {
            View view = hasViews.findViewById(com.widen.R.id.login);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.login();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.logined_lay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.logined_lay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.register);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.register();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.product_lay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.product_lay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.logined_lay2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.logined_lay2();
                    }

                }
                );
            }
        }
        afterViews();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public HomeFragment build() {
            HomeFragment_ fragment_ = new HomeFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}