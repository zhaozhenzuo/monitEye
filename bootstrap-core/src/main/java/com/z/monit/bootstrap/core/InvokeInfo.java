package com.z.monit.bootstrap.core;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程中存储的调用信息
 * 
 * @author zhaozhenzuo
 *
 */
public class InvokeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 调用链唯一id
	 */
	private String invokeUniqueKey;

	/**
	 * 当前结点父结点<br/>
	 */
	private String parentId;

	/**
	 * 当前id
	 */
	private String currentId;

	/**
	 * 当前调用id序列号
	 */
	private AtomicInteger currentInvokeSeq;
	
	public String getInvokeUniqueKey() {
		return invokeUniqueKey;
	}

	public void setInvokeUniqueKey(String invokeUniqueKey) {
		this.invokeUniqueKey = invokeUniqueKey;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String currentId) {
		this.currentId = currentId;
	}

	public AtomicInteger getCurrentInvokeSeq() {
		return currentInvokeSeq;
	}

	public void setCurrentInvokeSeq(AtomicInteger currentInvokeSeq) {
		this.currentInvokeSeq = currentInvokeSeq;
	}

}
