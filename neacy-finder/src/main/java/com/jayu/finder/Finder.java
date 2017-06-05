package com.jayu.finder;

import com.jayu.provider.Provider;

/**
 * @author yuzongxu
 * @since 2017/5/21
 */
public interface Finder<T> {
    void inject(T host, Object source, Provider provider);
}
