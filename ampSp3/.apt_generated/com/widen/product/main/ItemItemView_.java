//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.widen.product.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import com.widen.R.id;
import com.widen.R.layout;
import com.widen.widget.URLImageView;
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
public final class ItemItemView_
    extends ItemItemView
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public ItemItemView_(Context context) {
        super(context);
        init_();
    }

    public ItemItemView_(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_();
    }

    public static ItemItemView build(Context context) {
        ItemItemView_ instance = new ItemItemView_(context);
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
            inflate(getContext(), layout.item_item, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public static ItemItemView build(Context context, AttributeSet attrs) {
        ItemItemView_ instance = new ItemItemView_(context, attrs);
        instance.onFinishInflate();
        return instance;
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        name = ((TextView) hasViews.findViewById(id.name));
        delete = ((Button) hasViews.findViewById(id.delete));
        Expires = ((TextView) hasViews.findViewById(id.Expires));
        use = ((Button) hasViews.findViewById(id.use));
        InterestDescription = ((TextView) hasViews.findViewById(id.InterestDescription));
        HasExpired = ((TextView) hasViews.findViewById(id.HasExpired));
        img = ((URLImageView) hasViews.findViewById(id.img));
    }

}
