package com.jayu.finder;

import android.app.Activity;
import android.app.Fragment;
import android.util.ArrayMap;
import android.view.View;

import com.jayu.provider.ActivityProvider;
import com.jayu.provider.Provider;
import com.jayu.provider.ViewProvider;

/**
 * @author yuzongxu
 * @since 2017/5/21
 */
public class NeacyFinder {
    private static final String FINDER_SUFFIX = "$$Finder";
    private static final ActivityProvider ACTIVITY_PROVIDER = new ActivityProvider();
    private static final ViewProvider VIEW_PROVIDER = new ViewProvider();
    private static ArrayMap<String, Finder> mFinderArrayMap = new ArrayMap<>();

    public static void inject(Activity mAtivity) {
        inject(mAtivity, mAtivity, ACTIVITY_PROVIDER);
    }

    public static void inject(Fragment fragment, View view) {
        inject(fragment, view, VIEW_PROVIDER);
    }

    public static void inject(Object host, Object source, Provider provider) {
        String className = host.getClass().getName();
        try {
            Class<?> finderClass = Class.forName(className + FINDER_SUFFIX);
            Finder finder = mFinderArrayMap.get(className);
            if (finder == null) {
                finder = (Finder) finderClass.newInstance();
                mFinderArrayMap.put(className, finder);
            }
            finder.inject(host, source, provider);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
