package com.z.monit.bootstrap.core.agent;

import java.lang.instrument.Instrumentation;
import java.util.Map;

/**
 * 给agent的参数
 * 
 * @author zhaozhenzuo
 *
 */
public interface AgentParam {

	public Instrumentation getInstrument();
	
	
	public Map<String, Object> getConfigParam();

}
