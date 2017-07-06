package com.z.monit.agent.instrument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.monit.bootstrap.core.context.AgentContext;
import com.z.monit.bootstrap.core.instrument.InstrumentClass;
import com.z.monit.bootstrap.core.instrument.MonitPluginConfig;
import com.z.monit.bootstrap.core.instrument.MonitPlugin;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;

import javassist.CtClass;
import javassist.NotFoundException;

public class DefaultPluginInstrumentServiceImpl implements PluginInstrumentService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultPluginInstrumentServiceImpl.class);

	private ClassPoolService<CtClass> classPoolService;

	private AgentContext agentContext;

	private MonitPluginConfig monitPluginConfig;

	public DefaultPluginInstrumentServiceImpl(AgentContext agentContext, ClassPoolService<CtClass> classPoolService,
			MonitPluginConfig monitPluginConfig) {
		this.agentContext = agentContext;
		this.classPoolService = classPoolService;
		this.monitPluginConfig = monitPluginConfig;
	}

	/**
	 * 获取要织入的目标class
	 */
	public InstrumentClass getInstrumentClass(ClassLoader classLoader, String className, byte[] classfileBuffer) {
		try {
			CtClass ctClass = classPoolService.getCtClass(classLoader, className, classfileBuffer);
			return new DefaultInstrumentClass(ctClass, agentContext);
		} catch (NotFoundException e) {
			logger.error(">>no ctClass found,className:{},classLoader:{}", className, classLoader, e);
		}
		return null;
	}

}
