package com.z.monit.bootstrap.core.context;

import com.z.monit.bootstrap.core.AgentInfo;

/**
 * agent上下文<br/>
 * 职责:保存agentInfo,单次调用上下文等
 * 
 * @author zhaozhenzuo
 *
 */
public interface AgentContext {

	/**
	 * 获取agent信息
	 * 
	 * @return
	 */
	public AgentInfo agentInfo();

	/**
	 * 获取当前调用链context
	 * 
	 * @return
	 */
	public TraceContext currentTraceContext();

	/**
	 * 创建当前调用traceContext
	 * 
	 * @return
	 */
	public TraceContext createTraceContext();

}
