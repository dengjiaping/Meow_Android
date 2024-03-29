//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.widen.R.layout;
import com.widen.widget.XListView;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class TimeLineFragment_
    extends TimeLineFragment
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
            contentView_ = inflater.inflate(layout.time_line, container, false);
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

    public static TimeLineFragment_.FragmentBuilder_ builder() {
        return new TimeLineFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        line_bg = ((ImageView) hasViews.findViewById(com.widen.R.id.line_bg));
        today = ((Button) hasViews.findViewById(com.widen.R.id.today));
        xListView = ((XListView) hasViews.findViewById(com.widen.R.id.xListView));
        progressbar = ((ProgressBar) hasViews.findViewById(com.widen.R.id.progressbar));
        login_lay = ((FrameLayout) hasViews.findViewById(com.widen.R.id.login_lay));
        no_login_lay = ((LinearLayout) hasViews.findViewById(com.widen.R.id.no_login_lay));
        {
            View view = hasViews.findViewById(com.widen.R.id.goto_login);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TimeLineFragment_.this.goto_login();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.today);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TimeLineFragment_.this.today();
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

        public TimeLineFragment build() {
            TimeLineFragment_ fragment_ = new TimeLineFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
