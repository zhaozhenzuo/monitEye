package com.z.monit.bootstrap.core;

import java.io.Serializable;

public class InvokeParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private String transactionId;

	/**
	 * 父id,即调用者，由调用者生成，作为被调用者的parentId
	 */
	private String parentId;
	
	/**
	 * 业务唯一值
	 */
	private String bizUniqueKey;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBizUniqueKey() {
		return bizUniqueKey;
	}

	public void setBizUniqueKey(String bizUniqueKey) {
		this.bizUniqueKey = bizUniqueKey;
	}

}
