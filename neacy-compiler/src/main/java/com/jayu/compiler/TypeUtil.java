package com.jayu.compiler;

import com.squareup.javapoet.ClassName;

/**
 * @author yuzongxu
 * @since 2017/5/21
 */
public class TypeUtil {
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName FINDER = ClassName.get("com.jayu.finder", "Finder");
    public static final ClassName PROVIDER = ClassName.get("com.jayu.provider", "Provider");
}
