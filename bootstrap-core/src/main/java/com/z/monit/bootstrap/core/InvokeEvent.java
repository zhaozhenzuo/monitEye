package com.z.monit.bootstrap.core;

import java.io.Serializable;

public class InvokeEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String transactionId;

	private String curSpanId;

	private Long beginTime;

	private Long endTime;

	private Long cost;

	/**
	 * 调用角色<br/>
	 * {@link com.z.monit.bootstrap.core.constants.MonitInvokeEventRole}
	 */
	private Integer role;

	/**
	 * 服务调用方接口名称
	 */
	private String invokerInterfaceName;

	/**
	 * 服务被调用方接口名称
	 */
	private String acceptorInterfaceName;

	/**
	 * 调用结果状态<br/>
	 * {@link com.z.monit.bootstrap.core.constants.MonitInvokeResultStatus}
	 */
	private Integer status;

	/**
	 * 错误信息
	 */
	private String errmsg;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCurSpanId() {
		return curSpanId;
	}

	public void setCurSpanId(String curSpanId) {
		this.curSpanId = curSpanId;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getInvokerInterfaceName() {
		return invokerInterfaceName;
	}

	public void setInvokerInterfaceName(String invokerInterfaceName) {
		this.invokerInterfaceName = invokerInterfaceName;
	}

	public String getAcceptorInterfaceName() {
		return acceptorInterfaceName;
	}

	public void setAcceptorInterfaceName(String acceptorInterfaceName) {
		this.acceptorInterfaceName = acceptorInterfaceName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}
