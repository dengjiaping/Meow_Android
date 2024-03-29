//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MoreFragment_
    extends MoreFragment
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
            contentView_ = inflater.inflate(layout.more, container, false);
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

    public static MoreFragment_.FragmentBuilder_ builder() {
        return new MoreFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        login_lay = ((LinearLayout) hasViews.findViewById(com.widen.R.id.login_lay));
        no_login_lay = ((LinearLayout) hasViews.findViewById(com.widen.R.id.no_login_lay));
        {
            View view = hasViews.findViewById(com.widen.R.id.login);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MoreFragment_.this.login();
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
                        MoreFragment_.this.register();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.order_lay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MoreFragment_.this.order_lay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.login_out);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MoreFragment_.this.login_out();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.widen.R.id.account_lay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MoreFragment_.this.account_lay();
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

        public MoreFragment build() {
            MoreFragment_ fragment_ = new MoreFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
