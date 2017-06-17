package com.jayu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuzongxu <yuzongxu@xiaoyouzi.com>
 * @since 2017/6/17
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Intent {
    String value();
}
