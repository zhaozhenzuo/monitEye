/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.z.monit.bootstrap;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.Callable;

import com.z.monit.bootstrap.core.Agent;
import com.z.monit.bootstrap.core.agent.AgentParam;
import com.z.monit.bootstrap.core.classload.MonitClassLoader;

/**
 * 职责:<br/>
 * agent启动时,用于实例化Agent子类实例
 * 
 * @author zhaozhenzuo
 *
 */
public class AgentClassLoader {

	private final URLClassLoader classLoader;

	private String bootClass;

	private final ContextClassLoaderExecuteTemplate<Object> executeTemplate;

	public AgentClassLoader(URL[] urls) {
		if (urls == null) {
			throw new NullPointerException("urls");
		}

		ClassLoader bootStrapClassLoader = AgentClassLoader.class.getClassLoader();
		this.classLoader = createClassLoader(urls, bootStrapClassLoader);
		
		this.executeTemplate = new ContextClassLoaderExecuteTemplate<Object>(classLoader);
	}

	private MonitClassLoader createClassLoader(final URL[] urls, final ClassLoader bootStrapClassLoader) {
		return new MonitClassLoader(urls, bootStrapClassLoader);
	}

	public void setBootClass(String bootClass) {
		this.bootClass = bootClass;
	}

	/**
	 * 职责： 根据传入的agent参数，创建一个agent实例，默认为DefaultAgent实现。
	 * 
	 * @param agentOption
	 * @return
	 */
	public Agent boot(final AgentParam agentParam) {
		final Class<?> bootStrapClazz = getBootStrapClass();
		System.out.println(bootStrapClazz.getClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader());

		// 一定要用MonitClassLoader去实例化DefaultAgent，
		final Object agent = executeTemplate.execute(new Callable<Object>() {
			public Object call() throws Exception {
				try {
					System.out.println("threadload:" + Thread.currentThread().getContextClassLoader());
					System.out.println(Thread.currentThread().getContextClassLoader());
					System.out.println(bootStrapClazz.getClassLoader());
					Constructor<?> constructor = bootStrapClazz.getConstructor(AgentParam.class);
					return constructor.newInstance(agentParam);
				} catch (InstantiationException e) {
					throw new BootStrapException("boot create failed. Error:" + e.getMessage(), e);
				} catch (IllegalAccessException e) {
					throw new BootStrapException("boot method invoke failed. Error:" + e.getMessage(), e);
				}
			}
		});

		if (agent instanceof Agent) {
			return (Agent) agent;
		} else {
			String agentClassName;
			if (agent == null) {
				agentClassName = "Agent is null";
			} else {
				agentClassName = agent.getClass().getName();
			}
			throw new BootStrapException("Invalid AgentType. boot failed. AgentClass:" + agentClassName);
		}
	}

	/**
	 * 用pipoint自定义classload加载DefaultAgent类
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Class<?> getBootStrapClass() {
		try {
			Class class1 = classLoader.loadClass(bootClass);
			return class1;
		} catch (ClassNotFoundException e) {
			throw new BootStrapException("boot class not found. bootClass:" + bootClass + " Error:" + e.getMessage(),
					e);
		}
	}

}
