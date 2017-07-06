package com.z.monit.agent.instrument;

import com.z.monit.bootstrap.core.intercept.Interceptor;

public interface InterceptorRegister {

	/**
	 * 注册拦截器并返回注册后的拦截器id<br/>
	 * 注:拦截器id唯一
	 * 
	 * @param interceptor
	 * @return
	 */
	public int registerInterceptorAndGetId(final Interceptor interceptor);

	/**
	 * 根据id获取拦截器
	 * 
	 * @param id
	 * @return
	 */
	public Interceptor getInterceptorById(int id);

}
