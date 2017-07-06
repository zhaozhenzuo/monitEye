package com.z.monit.bootstrap.core.context;

/**
 * 单次调用上下文<br/>
 * 职责:给插件编写者,在实现相应逻辑时，提供获取此次调用链id,获取
 * 
 * @author zhaozhenzuo
 *
 */
public interface TraceContext {

	/**
	 * 获取单次调用链id
	 * 
	 * @return
	 */
	public String getTraceId();

	/**
	 * 下一个span，如:A->B，若A当前parentSpanId为-1,则产生的span parentId为-1,spanId为0
	 * 
	 * @return
	 */
	public Span nextSpan();

	/**
	 * 是否root
	 * 
	 * @return
	 */
	public Boolean isRoot();

}
