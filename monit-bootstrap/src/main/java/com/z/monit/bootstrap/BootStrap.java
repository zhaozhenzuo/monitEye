package com.z.monit.bootstrap;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootStrap {

	private static final Logger logger = LoggerFactory.getLogger(BootStrapStart.class);

	private static volatile AtomicBoolean startFlag = new AtomicBoolean(false);

	public static void premain(String agentOps, Instrumentation inst) {
		logger.info("=========================log");
		
		System.out.println("==================bootstrap premain");
		
		/**
		 * 1.检查是否已经启动
		 */
		if (!startFlag.compareAndSet(false, true)) {
			logger.warn(">>monit bootstrap already started!");
			return;
		}

		/**
		 * 2.检查是否含有bootstrap,boostrap-core,plugin等包<br/>
		 * TODO
		 */
		ClassPathResolver classPathResolver = new ClassPathResolver();
		boolean boostrapJarFindFlag = classPathResolver.initBootStrapJar();
		if (!boostrapJarFindFlag) {
			logger.error(">>not found boostrapJar");
			return;
		}

		/**
		 * 3.将bootstrap-core包加入到bootstrap搜索路径中
		 */
		boolean boostrapCoreFindFlag = classPathResolver.initBootStrapCore();
		if (!boostrapCoreFindFlag) {
			logger.error(">>no bootStrapCoreUrl for agent");
			return;
		}
		JarFile bootStrapCoreJarFile = getBootStrapCoreJarFile(
				classPathResolver.getBootstrapCoreFullUrl());
		if (bootStrapCoreJarFile == null) {
			logger.error(">>no bootStrapCoreJarFile for agent");
			return;
		}
		inst.appendToBootstrapClassLoaderSearch(bootStrapCoreJarFile);

		/**
		 * plugin初始化
		 */
		boolean pluginFindFlag = classPathResolver.initPlugin();
		if (!pluginFindFlag) {
			logger.error(">>no plugin found");
			return;
		}

		/**
		 * 默认agent实现libs的jar包初始化
		 */
		boolean defaultLibsFindFlag = classPathResolver.initLibUrls();
		if (!defaultLibsFindFlag) {
			logger.error(">>no libs found");
			return;
		}

		/**
		 * 4.将后续BootstrapStart transform加入到instrument中
		 */
		BootStrapStart bootStrapStart = new BootStrapStart(inst,classPathResolver);
		bootStrapStart.startInit();
	}

	private static JarFile getBootStrapCoreJarFile(String jarFileName) {
		try {
			return new JarFile(jarFileName);
		} catch (IOException e) {
			logger.error(">>not found bootstrap-core jar,jarFileName:" + jarFileName);
		}
		return null;
	}

}
