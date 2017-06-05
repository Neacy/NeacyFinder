package com.jayu.provider;

import android.app.Activity;
import android.view.View;

/**
 * Activity.findViewById
 *
 * @author yuzongxu
 * @since 2017/5/21
 */
public class ActivityProvider implements Provider {
    @Override
    public View findView(Object viewRoot, int view_id) {
        return ((Activity) viewRoot).findViewById(view_id);
    }
}
