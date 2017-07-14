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
	 * 接口名
	 */
	private String interfaceName;

	/**
	 * 调用结果状态<br/>
	 * {@link com.z.monit.bootstrap.core.constants.MonitInvokeResultStatus}
	 */
	private Integer status;

	/**
	 * 错误信息
	 */
	private String errmsg;

	/**
	 * 调用方ip
	 */
	private String invokerIp;

	private String parentId;

	private String methodName;

	private String nextSpanId;

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

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
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

	public String getInvokerIp() {
		return invokerIp;
	}

	public void setInvokerIp(String invokerIp) {
		this.invokerIp = invokerIp;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNextSpanId() {
		return nextSpanId;
	}

	public void setNextSpanId(String nextSpanId) {
		this.nextSpanId = nextSpanId;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder(128);
		buffer.append("transactionId:" + transactionId);
		buffer.append(",parentId:" + parentId);
		buffer.append(",spanId:" + curSpanId);
		buffer.append(",nextSpanId:" + nextSpanId);
		buffer.append(",interfaceName:" + interfaceName);
		buffer.append(",methodName:" + methodName);
		buffer.append(",role:" + role);
		buffer.append(",invokerIp:" + invokerIp);
		buffer.append(",beginTime:" + beginTime);
		buffer.append(",cost:" + cost);

		return buffer.toString();
	}

}
