package com.z.monit.agent;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.monit.agent.context.DefaultAgentContext;
import com.z.monit.agent.instrument.ClassPoolService;
import com.z.monit.agent.instrument.DefaultClassFileTransformDispatcher;
import com.z.monit.agent.instrument.DefaultPluginInstrumentServiceImpl;
import com.z.monit.agent.instrument.DefaultTransformCallbackRegister;
import com.z.monit.agent.instrument.JavaSsistClassPoolServiceImpl;
import com.z.monit.bootstrap.core.Agent;
import com.z.monit.bootstrap.core.AgentInfo;
import com.z.monit.bootstrap.core.agent.AgentParam;
import com.z.monit.bootstrap.core.classload.ClassLoaderUtil;
import com.z.monit.bootstrap.core.classload.PluginLoaderUtil;
import com.z.monit.bootstrap.core.context.AgentContext;
import com.z.monit.bootstrap.core.instrument.ClassFileTransformDispatcher;
import com.z.monit.bootstrap.core.instrument.MonitPlugin;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;
import com.z.monit.bootstrap.core.instrument.TransformCallback;
import com.z.monit.bootstrap.core.instrument.TransformCallbackRegister;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class DefaultAgent implements Agent {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAgent.class);

	private AgentParam agentParam;

	private AgentContext agentContext;

	private TransformCallbackRegister transformCallbackRegister;

	private ClassFileTransformDispatcher classFileTransformDispatcher;

	private PluginInstrumentService instrumentService;

	public DefaultAgent(AgentParam agentParam) {
		logger.info(">>defaultAgent create");
		this.agentParam = agentParam;

		/**
		 * 初始化
		 */
		this.init();
	}

	private void init() {
		logger.info(">>start init agent");

		/**
		 * 1.实例化相应处理类
		 */
		/***************** 实例化相应处理类start ***********************/
		/**
		 * 1.1实例化transformCallbackRegister注册器
		 */
		transformCallbackRegister = new DefaultTransformCallbackRegister();

		/**
		 * 1.2.初始化instrumentService
		 */
		this.initInstrumentService();

		/**
		 * 1.3.实例化织入分发器
		 */
		classFileTransformDispatcher = new DefaultClassFileTransformDispatcher(transformCallbackRegister,
				instrumentService);

		/***************** 实例化相应处理类end ***********************/

		/**
		 * 2.初始化agentContext
		 */
		boolean initAgentContextFlag = this.initAgentContext();
		if (!initAgentContextFlag) {
			logger.error(">>init agentContext fail");
			return;
		}

		/**
		 * 3.注册plugin transformCallback到注册器中
		 */
		boolean initPluginFlag = this.initPlugin();
		if (!initPluginFlag) {
			logger.error(">>init plugin fail");
			return;
		}

		/**
		 * 4.将织入分发器加入到instrument中,之后webAppClassLoader加载应用类时会调用织入分发器逻辑
		 */
		agentParam.getInstrument().addTransformer(classFileTransformDispatcher);
		
		ClassLoaderUtil.monitClassLoader=Thread.currentThread().getContextClassLoader();
		
		logger.info(">>end init agent");

	}

	private void initInstrumentService() {
		/**
		 * 1.实例化classPoolService,用于查找相应类的CtClass
		 */
		ClassPool rootClassPool = new ClassPool(true);
		ClassPoolService<CtClass> classPoolService = new JavaSsistClassPoolServiceImpl(rootClassPool);

		/**
		 * 2.创建rootClassPool,放入classPoolService,
		 * 这个rootClassPool能够加载到appClassLoader能够加载到的类,bootstrap-core能够加载到的类,
		 * 及monit-agent能够加载的类
		 */
		// 增加bootstrap-core
		try {
			rootClassPool.appendClassPath((String) agentParam.getConfigParam().get("bootstrapCoreFullUrl"));
		} catch (NotFoundException e) {
			logger.error(">>no bootstrap core jar found", e);
		}

		// 增加monit-agent jar
		rootClassPool.appendClassPath(new ClassClassPath(this.getClass()));

		/**
		 * 3.实例化instrumentService<br/>
		 * TODO,monitAgentConfig
		 */
		instrumentService = new DefaultPluginInstrumentServiceImpl(agentContext, classPoolService, null);
	}

	/**
	 * 初始化agentContext<br/>
	 * TODO
	 */
	private boolean initAgentContext() {
		AgentInfo agentInfo = new AgentInfo();
		agentInfo.setStartTime(new Date());
		agentContext = new DefaultAgentContext(agentInfo);
		return true;
	}

	private boolean initPlugin() {
		/**
		 * 1.查找plugin
		 */
		List<MonitPlugin> monitPluginList = PluginLoaderUtil.load(MonitPlugin.class,
				(URL[]) agentParam.getConfigParam().get("pluginUrls"));
		if (monitPluginList == null || monitPluginList.size() <= 0) {
			logger.error(">>not plugin found,agent init fail");
			return false;
		}

		logger.info(">>found plugin:" + monitPluginList.toString());

		/**
		 * 2.将对应plugin中要织入的transformCallback注册到织入注册器中
		 */
		for (MonitPlugin monitPlugin : monitPluginList) {
			logger.info(">>register ");

			Map<String, TransformCallback> transformCallbackMap = monitPlugin.addTransformCallBack();
			if (transformCallbackMap == null || transformCallbackMap.isEmpty()) {
				logger.error(">>error addTransformCallBack,no transformCallback found");
				return false;
			}

			for (Entry<String, TransformCallback> entry : transformCallbackMap.entrySet()) {
				transformCallbackRegister.registerTransform(entry.getKey(), entry.getValue());
			}
		}

		/**
		 * 3.设置monitClassLoader到codeGen中
		 */
//		InstrumentCodeUtil.monitClassLoader = Thread.currentThread().getContextClassLoader();

		return true;
	}

	public void start() {
		logger.info(">>do start agent");

	}

	public void stop() {
		logger.info(">>do stop agent");

	}

	public AgentContext getAgentContext() {
		return agentContext;
	}

	public ClassFileTransformDispatcher getClassFileTransformDispatcherForTest() {
		return this.classFileTransformDispatcher;
	}

}
