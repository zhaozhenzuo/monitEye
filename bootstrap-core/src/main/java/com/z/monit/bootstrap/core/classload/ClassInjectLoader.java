package com.z.monit.bootstrap.core.classload;

/**
 * 定义通过往某个classloader上增加url的形式,来用其加载增加的url中的类
 * 
 * @author zhaozhenzuo
 *
 */
public interface ClassInjectLoader {

	/**
	 * 通过往classLoader中加相应url,之后从classLoader加载指定className的类
	 * 
	 * @param classLoader
	 * @param className
	 * @return
	 */
	public Class<?> loadClassByInjectUrl(ClassLoader classLoader, String className);

}
