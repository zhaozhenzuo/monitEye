package com.z.monit.bootstrap.core.instrument;

/**
 * 对外提供对要织入类相关操作方法
 * 
 * @author zhaozhenzuo
 *
 */
public interface InstrumentClass {

	public void addMethod(String methodName, String...args);

	public InstrumentMethod getDeclairedMethod(String methodName, String... args);

}
