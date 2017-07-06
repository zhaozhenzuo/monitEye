package com.z.monit.bootstrap.core.classload;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责加载agent包，及agent所在log4j，plugin jar
 * 
 * @author zhaozhenzuo
 *
 */
public class MonitClassLoader extends URLClassLoader {

	private static final List<String> classShouldLoadedByAgentClassLoader = new ArrayList<String>();

	private ClassLoader parent;

	static {
		classShouldLoadedByAgentClassLoader.add("com.z.monit.agent");
	}

	public MonitClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		this.parent = parent;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class clazz = findLoadedClass(name);
		if (clazz == null) {
			if (shouldLoadByAgentClassLoader(name)) {
				clazz = findClass(name);
			} else {
				try {
					clazz = parent.loadClass(name);
				} catch (ClassNotFoundException ignore) {
				}
				if (clazz == null) {
					clazz = findClass(name);
				}
			}
		}
		if (resolve) {
			resolveClass(clazz);
		}
		return clazz;
	}

	private boolean shouldLoadByAgentClassLoader(String className) {
		for (String whiteListValue : classShouldLoadedByAgentClassLoader) {
			if (className.indexOf(whiteListValue) == 0) {
				return true;
			}
		}
		return false;
	}

}
