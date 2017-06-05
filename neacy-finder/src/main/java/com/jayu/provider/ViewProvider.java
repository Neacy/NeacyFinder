package com.jayu.provider;

import android.view.View;

/**
 * View.findViewById
 *
 * @author yuzongxu
 * @since 2017/5/21
 */
public class ViewProvider implements Provider {
    @Override
    public View findView(Object viewRoot, int view_id) {
        return ((View) viewRoot).findViewById(view_id);
    }
}
