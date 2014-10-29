//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import java.io.Serializable;
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
import com.widen.http.model.BaseListInfo;
import com.widen.http.model.ItemInfo;
import com.widen.http.model.OrderInfo;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class OrderListAct_
    extends OrderListAct
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String ITEM_INFO_EXTRA = "itemInfo";
    public final static String FLAG_EXTRA = "flag";
    public final static String INFOS_EXTRA = "info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.order_list);
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

    public static OrderListAct_.IntentBuilder_ intent(Context context) {
        return new OrderListAct_.IntentBuilder_(context);
    }

    public static OrderListAct_.IntentBuilder_ intent(Fragment supportFragment) {
        return new OrderListAct_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        no_data_lay = ((LinearLayout) hasViews.findViewById(id.no_data_lay));
        progressbar = ((ProgressBar) hasViews.findViewById(id.progressbar));
        listview = ((ListView) hasViews.findViewById(id.listview));
        {
            View view = hasViews.findViewById(id.back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        OrderListAct_.this.back();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @SuppressWarnings("unchecked")
    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(ITEM_INFO_EXTRA)) {
                itemInfo = ((ItemInfo) extras_.getSerializable(ITEM_INFO_EXTRA));
            }
            if (extras_.containsKey(FLAG_EXTRA)) {
                flag = extras_.getString(FLAG_EXTRA);
            }
            if (extras_.containsKey(INFOS_EXTRA)) {
                infos = ((BaseListInfo<OrderInfo> ) extras_.getSerializable(INFOS_EXTRA));
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
            intent_ = new Intent(context, OrderListAct_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, OrderListAct_.class);
        }

        public Intent get() {
            return intent_;
        }

        public OrderListAct_.IntentBuilder_ flags(int flags) {
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

        public OrderListAct_.IntentBuilder_ itemInfo(ItemInfo itemInfo) {
            intent_.putExtra(ITEM_INFO_EXTRA, ((Serializable) itemInfo));
            return this;
        }

        public OrderListAct_.IntentBuilder_ flag(String flag) {
            intent_.putExtra(FLAG_EXTRA, flag);
            return this;
        }

        public OrderListAct_.IntentBuilder_ infos(BaseListInfo<OrderInfo> infos) {
            intent_.putExtra(INFOS_EXTRA, ((Serializable) infos));
            return this;
        }

    }

}
