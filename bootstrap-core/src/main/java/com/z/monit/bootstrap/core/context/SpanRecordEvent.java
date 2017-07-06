package com.z.monit.bootstrap.core.context;

import java.io.Serializable;

/**
 * 定义一个用于发送到收集器中的调调用事件<br/>
 * 格式:<br/>
 * monit类型(如dubbo)+调用链事务traceId+此次调用事件spanId+调用方host+调用服务名＋调用的方法名+调用参数+被调用方host
 * +耗时+异常信息
 * 
 * @author zhaozhenzuo
 *
 */
public class SpanRecordEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * monit类型,eg:dubbo
	 */
	private String monitType;

	/**
	 * 调用链id
	 */
	private String traceId;

	/**
	 * 父spanId
	 */
	private String parentSpanId;

	/**
	 * 当前span id
	 */
	private String spanId;

	/**
	 * 耗时
	 */
	private Long cost;

	/**
	 * 调用方host
	 */
	private String senderHost;

	/**
	 * 调用方service
	 */
	private String senderService;

	/**
	 * 接收方host
	 */
	private String recieverHost;

	/**
	 * 接收方service
	 */
	private String recieverService;

	/**
	 * 是否调用方
	 */
	private Boolean isSender;

	public String getMonitType() {
		return monitType;
	}

	public void setMonitType(String monitType) {
		this.monitType = monitType;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getParentSpanId() {
		return parentSpanId;
	}

	public void setParentSpanId(String parentSpanId) {
		this.parentSpanId = parentSpanId;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public String getSenderHost() {
		return senderHost;
	}

	public void setSenderHost(String senderHost) {
		this.senderHost = senderHost;
	}

	public String getSenderService() {
		return senderService;
	}

	public void setSenderService(String senderService) {
		this.senderService = senderService;
	}

	public String getRecieverHost() {
		return recieverHost;
	}

	public void setRecieverHost(String recieverHost) {
		this.recieverHost = recieverHost;
	}

	public String getRecieverService() {
		return recieverService;
	}

	public void setRecieverService(String recieverService) {
		this.recieverService = recieverService;
	}

	public Boolean getIsSender() {
		return isSender;
	}

	public void setIsSender(Boolean isSender) {
		this.isSender = isSender;
	}

}
