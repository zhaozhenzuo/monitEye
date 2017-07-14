package com.z.monit.bootstrap.core.intercept;

/**
 * 拦截器接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface Interceptor {
	
	void before(Object target, Object args);

	void after(Object target, Object args, Object result, Throwable throwable);

}
