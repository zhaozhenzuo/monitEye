package com.z.monit.bootstrap.core.context;

import java.io.Serializable;

/**
 * 单次调用的唯一标识span<br/>
 * eg:A调B,则A生成一个span id
 * 
 * @author zhaoz henzuo
 *
 */
public class Span implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 父span id
	 */
	private String parentSpanId;

	/**
	 * 当前spanId
	 */
	private String spanId;
	
	/**
	 * 是否是发送者，即服务调用方
	 */
	private boolean isSender;

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

	public boolean isSender() {
		return isSender;
	}

	public void setSender(boolean isSender) {
		this.isSender = isSender;
	}

}
