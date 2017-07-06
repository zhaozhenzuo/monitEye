package com.z.monit.bootstrap.core.context;

import java.io.Serializable;

/**
 * 单次调用事件
 * 
 * @author zhaozhenzuo
 *
 */
public class SpanEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 要传递的span
	 */
	private Span span;

	public Span getSpan() {
		return span;
	}

	public void setSpan(Span span) {
		this.span = span;
	}
	
	
}
