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
import android.widget.EditText;
import android.widget.ProgressBar;
import com.widen.R.id;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class RegisterStepOneAct_
    extends RegisterStepOneAct
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.register_step_one);
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

    public static RegisterStepOneAct_.IntentBuilder_ intent(Context context) {
        return new RegisterStepOneAct_.IntentBuilder_(context);
    }

    public static RegisterStepOneAct_.IntentBuilder_ intent(Fragment supportFragment) {
        return new RegisterStepOneAct_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        phone = ((EditText) hasViews.findViewById(id.phone));
        progressbar = ((ProgressBar) hasViews.findViewById(id.progressbar));
        {
            View view = hasViews.findViewById(id.back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RegisterStepOneAct_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.sub);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RegisterStepOneAct_.this.sub();
                    }

                }
                );
            }
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, RegisterStepOneAct_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, RegisterStepOneAct_.class);
        }

        public Intent get() {
            return intent_;
        }

        public RegisterStepOneAct_.IntentBuilder_ flags(int flags) {
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
