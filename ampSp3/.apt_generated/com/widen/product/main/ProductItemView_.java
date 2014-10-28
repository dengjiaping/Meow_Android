//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.widen.R.id;
import com.widen.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;


/**
 * We use @SuppressWarning here because our java code
 * generator doesn't know that there is no need
 * to import OnXXXListeners from View as we already
 * are in a View.
 * 
 */
@SuppressWarnings("unused")
public final class ProductItemView_
    extends ProductItemView
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public ProductItemView_(Context context) {
        super(context);
        init_();
    }

    public ProductItemView_(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_();
    }

    public static ProductItemView build(Context context) {
        ProductItemView_ instance = new ProductItemView_(context);
        instance.onFinishInflate();
        return instance;
    }

    /**
     * The mAlreadyInflated_ hack is needed because of an Android bug
     * which leads to infinite calls of onFinishInflate()
     * when inflating a layout with a parent and using
     * the <merge /> tag.
     * 
     */
    @Override
    public void onFinishInflate() {
        if (!alreadyInflated_) {
            alreadyInflated_ = true;
            inflate(getContext(), layout.product_item, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public static ProductItemView build(Context context, AttributeSet attrs) {
        ProductItemView_ instance = new ProductItemView_(context, attrs);
        instance.onFinishInflate();
        return instance;
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        product_progressbar_lay = ((FrameLayout) hasViews.findViewById(id.product_progressbar_lay));
        bank_txt = ((TextView) hasViews.findViewById(id.bank_txt));
        SellingStatus = ((ImageView) hasViews.findViewById(id.SellingStatus));
        product_progressbar = ((ProgressBar) hasViews.findViewById(id.product_progressbar));
        Duration = ((TextView) hasViews.findViewById(id.Duration));
        Yield_txt = ((TextView) hasViews.findViewById(id.Yield_txt));
        BankName = ((TextView) hasViews.findViewById(id.BankName));
        Name = ((TextView) hasViews.findViewById(id.Name));
        Yield = ((TextView) hasViews.findViewById(id.Yield));
        being = ((TextView) hasViews.findViewById(id.being));
        TotalNumber = ((TextView) hasViews.findViewById(id.TotalNumber));
        Date = ((TextView) hasViews.findViewById(id.Date));
    }

}
