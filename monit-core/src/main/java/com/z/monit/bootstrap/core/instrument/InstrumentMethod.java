package com.z.monit.bootstrap.core.instrument;

/**
 * 对方法增强操作类
 * 
 * @author zhaozhenzuo
 *
 */
public interface InstrumentMethod {

	/**
	 * 对当前方法加入拦截器
	 * 
	 * @param interceptorName
	 *            拦截器名称
	 */
	public void addInterceptor(String interceptorName);

}
