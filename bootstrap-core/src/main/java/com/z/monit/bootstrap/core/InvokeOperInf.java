package com.z.monit.bootstrap.core;

/**
 * 调用操作接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface InvokeOperInf {

	/**
	 * 获取当前线程中的调用信息
	 * 
	 * @return
	 */
	public InvokeInfo getInvokerInfoCurThread();

	/**
	 * 获取当前线程中的调用信息，如果没有则创建一个调用信息存储在当前线程中
	 * 
	 * @param invokeParam
	 * @return
	 */
	public InvokeInfo getOrCreateInvokerInfoCurThread(final InvokeParam invokeParam);

	/**
	 * 构建调用参数
	 * 
	 * @param bizUniqueKey
	 *            业务唯一值
	 * @return
	 */
	public InvokeParam composeInvokeParamAndIncreaseCurInvokeSeqForInvoker(String bizUniqueKey);

}
