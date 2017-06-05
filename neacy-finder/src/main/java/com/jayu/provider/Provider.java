package com.jayu.provider;

import android.view.View;

/**
 * @author yuzongxu
 * @since 2017/5/21
 */
public interface Provider {
    View findView(Object viewRoot, int view_id);
}
