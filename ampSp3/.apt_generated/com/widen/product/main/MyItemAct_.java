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
import android.widget.ListView;
import android.widget.ProgressBar;
import com.widen.R.id;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MyItemAct_
    extends MyItemAct
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String FLAG_EXTRA = "flag";
    public final static String ORDER_ID_EXTRA = "orderId";
    public final static String ORDER_TITLE_EXTRA = "orderTitle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.my_item);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectExtras_();
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

    public static MyItemAct_.IntentBuilder_ intent(Context context) {
        return new MyItemAct_.IntentBuilder_(context);
    }

    public static MyItemAct_.IntentBuilder_ intent(Fragment supportFragment) {
        return new MyItemAct_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        listview = ((ListView) hasViews.findViewById(id.listview));
        progressbar = ((ProgressBar) hasViews.findViewById(id.progressbar));
        no_data_lay = ((LinearLayout) hasViews.findViewById(id.no_data_lay));
        {
            View view = hasViews.findViewById(id.back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MyItemAct_.this.back();
                    }

                }
                );
            }
        }
        afterViews();
    }

    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(FLAG_EXTRA)) {
                flag = extras_.getString(FLAG_EXTRA);
            }
            if (extras_.containsKey(ORDER_ID_EXTRA)) {
                orderId = extras_.getString(ORDER_ID_EXTRA);
            }
            if (extras_.containsKey(ORDER_TITLE_EXTRA)) {
                orderTitle = extras_.getString(ORDER_TITLE_EXTRA);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, MyItemAct_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, MyItemAct_.class);
        }

        public Intent get() {
            return intent_;
        }

        public MyItemAct_.IntentBuilder_ flags(int flags) {
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

        public MyItemAct_.IntentBuilder_ flag(String flag) {
            intent_.putExtra(FLAG_EXTRA, flag);
            return this;
        }

        public MyItemAct_.IntentBuilder_ orderId(String orderId) {
            intent_.putExtra(ORDER_ID_EXTRA, orderId);
            return this;
        }

        public MyItemAct_.IntentBuilder_ orderTitle(String orderTitle) {
            intent_.putExtra(ORDER_TITLE_EXTRA, orderTitle);
            return this;
        }

    }

}
