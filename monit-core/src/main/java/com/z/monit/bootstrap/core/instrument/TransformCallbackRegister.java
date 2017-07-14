package com.z.monit.bootstrap.core.instrument;

/**
 * 织入transform注册器
 * 
 * @author zhaozhenzuo
 *
 */
public interface TransformCallbackRegister {

	/**
	 * 将织入回调器注册到注册器中
	 * 
	 * @param classNameInjected
	 *            被识入目标类名
	 * @param transformCallBack
	 *            业务方实现的织入逻辑
	 */
	public void registerTransform(String classNameInjected, TransformCallback transformCallBack);

	/**
	 * 根据要织入的目标类名获取织入逻辑callback
	 * 
	 * @param classNameInjected
	 * @return
	 */
	public TransformCallback geTransformCallbackByClassNameInjected(String classNameInjected);

}
