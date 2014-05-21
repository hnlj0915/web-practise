package com.lj.common.spring.aop.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MCache {

	// 默认key会根据方法名和方法参数值自动生成
	public String key() default "";

	// 默认设置缓存有效时间 60min 单位s
	public int exp() default 2 * 61;

	// 默认设置缓存历史有效时间 24Hour 单位s
	public int historyExp() default 15 * 61 * 67;
}
