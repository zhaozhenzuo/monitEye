package com.z.monit.agent.context;

import com.z.monit.bootstrap.core.AgentInfo;
import com.z.monit.bootstrap.core.context.AgentContext;

public class DefaultAgentContext implements AgentContext {

	private AgentInfo agentInfo;

	public DefaultAgentContext(AgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}

	public AgentInfo agentInfo() {
		return agentInfo;
	}

}
