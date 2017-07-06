package com.z.monit.bootstrap.core.intercept;

import java.lang.reflect.Method;

/**
 * 定义能够在目标对象方法执行前后，实现一些业务逻辑的interceptor接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface AroundInterceptor extends Interceptor {

	/**
	 * 在目标对象方法执行前，执行的织入逻辑
	 * 
	 * @param target
	 *            目标对象
	 * @param method
	 *            被拦截方法
	 * @param args
	 *            参数
	 */
	public void before(Object target, Method method, Object... args);

	/**
	 * 在目标对象方法执行后，执行的织入逻辑
	 * 
	 * @param target
	 *            目标对象
	 * @param method
	 *            被拦截方法
	 * @param args
	 *            参数
	 */
	public void after(Object target, Method method, Throwable throwable, Object... args);

}
