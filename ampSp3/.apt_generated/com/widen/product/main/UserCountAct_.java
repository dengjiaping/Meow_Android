//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.widen.R.id;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class UserCountAct_
    extends UserCountAct
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.user_count);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static UserCountAct_.IntentBuilder_ intent(Context context) {
        return new UserCountAct_.IntentBuilder_(context);
    }

    public static UserCountAct_.IntentBuilder_ intent(Fragment supportFragment) {
        return new UserCountAct_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        no_user_lay = ((LinearLayout) hasViews.findViewById(id.no_user_lay));
        IdCard = ((TextView) hasViews.findViewById(id.IdCard));
        InvestingPrice = ((TextView) hasViews.findViewById(id.InvestingPrice));
        RealName = ((TextView) hasViews.findViewById(id.RealName));
        ExpectedEarnings = ((TextView) hasViews.findViewById(id.ExpectedEarnings));
        Earnings = ((TextView) hasViews.findViewById(id.Earnings));
        progressbar = ((ProgressBar) hasViews.findViewById(id.progressbar));
        phone = ((TextView) hasViews.findViewById(id.phone));
        data_lay = ((LinearLayout) hasViews.findViewById(id.data_lay));
        no_data_lay = ((LinearLayout) hasViews.findViewById(id.no_data_lay));
        Cellphone = ((TextView) hasViews.findViewById(id.Cellphone));
        user_lay = ((LinearLayout) hasViews.findViewById(id.user_lay));
        {
            View view = hasViews.findViewById(id.back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserCountAct_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.go_to_product);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserCountAct_.this.go_to_product();
                    }

                }
                );
            }
        }
        afterViews();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, UserCountAct_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, UserCountAct_.class);
        }

        public Intent get() {
            return intent_;
        }

        public UserCountAct_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (context_ instanceof Activity) {
                    ((Activity) context_).startActivityForResult(intent_, requestCode);
                } else {
                    context_.startActivity(intent_);
                }
            }
        }

    }

}
