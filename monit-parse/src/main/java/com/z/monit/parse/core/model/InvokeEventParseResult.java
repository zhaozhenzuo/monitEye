package com.z.monit.parse.core.model;

import java.io.Serializable;

/**
 * 监控日志分析结果
 * 
 * @author zhaozhenzuo
 *
 */
public class InvokeEventParseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String transactionId;

	private String spanId;

	private String parentSpanId;

	/**
	 * 接口名
	 */
	private String interfaceName;

	private String methodName;

	private Long beginTimeClientSide;

	private Long endTimeClientSide;

	private Long costForAll;

	private Long beginTimeServerSide;

	private Long endTimeServerSide;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getParentSpanId() {
		return parentSpanId;
	}

	public void setParentSpanId(String parentSpanId) {
		this.parentSpanId = parentSpanId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Long getBeginTimeClientSide() {
		return beginTimeClientSide;
	}

	public void setBeginTimeClientSide(Long beginTimeClientSide) {
		this.beginTimeClientSide = beginTimeClientSide;
	}

	public Long getEndTimeClientSide() {
		return endTimeClientSide;
	}

	public void setEndTimeClientSide(Long endTimeClientSide) {
		this.endTimeClientSide = endTimeClientSide;
	}

	public Long getCostForAll() {
		return costForAll;
	}

	public void setCostForAll(Long costForAll) {
		this.costForAll = costForAll;
	}

	public Long getBeginTimeServerSide() {
		return beginTimeServerSide;
	}

	public void setBeginTimeServerSide(Long beginTimeServerSide) {
		this.beginTimeServerSide = beginTimeServerSide;
	}

	public Long getEndTimeServerSide() {
		return endTimeServerSide;
	}

	public void setEndTimeServerSide(Long endTimeServerSide) {
		this.endTimeServerSide = endTimeServerSide;
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

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder(128);
		buffer.append("transactionId:" + transactionId);
		buffer.append(",parentSpanId:" + parentSpanId);
		buffer.append(",spanId:" + spanId);
		buffer.append(",interfaceName:" + interfaceName);
		buffer.append(",methodName:" + methodName);
		buffer.append(",invokerIp:" + invokerIp);
		buffer.append(",beginTimeClientSide:" + beginTimeClientSide);
		buffer.append(",endTimeClientSide:" + endTimeClientSide);
		buffer.append(",costForAll:" + costForAll);

		return buffer.toString();
	}

}
