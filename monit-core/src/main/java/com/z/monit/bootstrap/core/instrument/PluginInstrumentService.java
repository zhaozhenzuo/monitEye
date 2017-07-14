package com.z.monit.bootstrap.core.instrument;

/**
 * 对plugin编写人员提供的织入接口,每个plugin都有自己独立的instrumentService
 * 
 * @author zhaozhenzuo
 *
 */
public interface PluginInstrumentService {

	/**
	 * 获取织入处理类
	 * 
	 * @param classLoader
	 * @param className
	 * @param classfileBuffer
	 * @return
	 */
	public InstrumentClass getInstrumentClass(ClassLoader classLoader, String className, byte[] classfileBuffer);

}
