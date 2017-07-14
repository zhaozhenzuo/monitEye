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
	private String transactionId;

	/**
	 * 当前结点父结点<br/>
	 */
	private String parentSpanId;

	/**
	 * 当前span id
	 */
	private String currentSpanId;

	/**
	 * 当前调用id序列号
	 */
	private AtomicInteger currentInvokeSeq;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getParentSpanId() {
		return parentSpanId;
	}

	public void setParentSpanId(String parentSpanId) {
		this.parentSpanId = parentSpanId;
	}

	public String getCurrentSpanId() {
		return currentSpanId;
	}

	public void setCurrentSpanId(String currentSpanId) {
		this.currentSpanId = currentSpanId;
	}

	public AtomicInteger getCurrentInvokeSeq() {
		return currentInvokeSeq;
	}

	public void setCurrentInvokeSeq(AtomicInteger currentInvokeSeq) {
		this.currentInvokeSeq = currentInvokeSeq;
	}

}
