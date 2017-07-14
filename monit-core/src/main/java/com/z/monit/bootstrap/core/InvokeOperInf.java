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
	 * 构建调用参数<br/>
	 * 
	 * @param transactionId
	 *            调用链id
	 * @param parentSpanId
	 *            作为被调用者的父类id
	 * @param bizUniqueKey
	 * @return
	 */
	public InvokeParam composeInvokeParamAndIncreaseCurInvokeSeqForInvoker(String transactionId, String parentSpanId,
			String bizUniqueKey);

	/**
	 * 清除当前线程调用信息
	 */
	public void clearInvokerInfoCurThread();

	/**
	 * 增加当前curSpanId并返回<br/>
	 */
	public String nextSpanId();

}
