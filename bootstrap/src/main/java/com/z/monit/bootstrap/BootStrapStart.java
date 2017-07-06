package com.z.monit.bootstrap;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;

import com.z.monit.bootstrap.core.Agent;
import com.z.monit.bootstrap.core.agent.AgentParam;
import com.z.monit.bootstrap.core.agent.DefaultAgentParam;

public class BootStrapStart {

	private ClassPathResolver classPathResolver;

	private static final String DEFAULT_AGENT = "com.z.monit.agent.DefaultAgent";

	private Instrumentation instrumentation;

	/**
	 * TEST<br/>
	 * TODO
	 */
	public static Agent agent;

	public BootStrapStart(Instrumentation instrumentation, ClassPathResolver classPathResolver) {
		this.instrumentation = instrumentation;
		this.classPathResolver = classPathResolver;
	}

	public void startInit() {
		/**
		 * 创建AgentClassLoader用于加载Agent子类,并通过它实例化
		 */
		AgentClassLoader agentClassLoader = new AgentClassLoader(classPathResolver.getLibUrls());
		String bootClaz = DEFAULT_AGENT;
		agentClassLoader.setBootClass(bootClaz);

		/**
		 * 实例化Agent子类
		 */
		Map<String, Object> configParamMap = new HashMap<String, Object>();
		configParamMap.put("bootstrapCoreFullUrl", classPathResolver.getBootstrapCoreFullUrl());
		configParamMap.put("pluginUrls", classPathResolver.getPluginUrls());
		
		AgentParam agentParam = new DefaultAgentParam(instrumentation);
		DefaultAgentParam.configMap=configParamMap;
		agent = agentClassLoader.boot(agentParam);

		/**
		 * 启动agent
		 */
		agent.start();
	}

}
