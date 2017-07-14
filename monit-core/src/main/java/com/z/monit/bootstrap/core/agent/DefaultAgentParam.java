package com.z.monit.bootstrap.core.agent;

import java.lang.instrument.Instrumentation;
import java.util.Map;

public class DefaultAgentParam implements AgentParam {

	private Instrumentation instrumentation;

	public static Map<String, Object> configMap;

	public DefaultAgentParam(Instrumentation inst) {
		instrumentation = inst;
	}

	public Instrumentation getInstrument() {
		return instrumentation;
	}

	public Map<String, Object> getConfigParam() {
		return configMap;
	}

}
