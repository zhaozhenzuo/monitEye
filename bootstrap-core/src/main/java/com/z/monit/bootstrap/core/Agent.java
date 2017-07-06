package com.z.monit.bootstrap.core;

import com.z.monit.bootstrap.core.context.AgentContext;
import com.z.monit.bootstrap.core.instrument.ClassFileTransformDispatcher;

/**
 * agent进程接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface Agent {

	/**
	 * 启动agent
	 */
	public void start();

	/**
	 * 停止agent
	 */
	public void stop();

	/**
	 * 获取agent全局上下文<br/>
	 * 含agentInfo
	 * 
	 * @return
	 */
	public AgentContext getAgentContext();

	/**
	 * just for test transform<br/>
	 * TODO
	 * @return
	 */
	public ClassFileTransformDispatcher getClassFileTransformDispatcherForTest();
}
