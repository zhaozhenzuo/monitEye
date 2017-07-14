package com.z.monit.bootstrap.core.classload;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.z.monit.bootstrap.core.instrument.MonitPlugin;

public class PluginLoaderUtil {
	
	public static void main(String[] args) throws MalformedURLException {
		URL[] urls=new URL[1];
		
		URL url=new URL("file:/Users/zhaozhenzuo/Documents/project/myMonit/monit/monit-plugins/dubbo-plugin/target/dubbo-plugin-1.0.jar");
		urls[0]=url;
		
		List<MonitPlugin> resList=load(MonitPlugin.class, urls);
		System.out.println(resList);
	}

	public static <T> List<T> load(Class<T> serviceType, URL[] urls) {
		URLClassLoader classLoader = createPluginClassLoader(urls, Thread.currentThread().getContextClassLoader());
		return load(serviceType, classLoader);
	}

	private static PluginLoaderClassLoader createPluginClassLoader(final URL[] urls, final ClassLoader parent) {
		return new PluginLoaderClassLoader(urls, parent);
	}

	public static <T> List<T> load(Class<T> serviceType, ClassLoader classLoader) {
		ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceType,classLoader);

		List<T> plugins = new ArrayList<T>();
		for (T plugin : serviceLoader) {
			plugins.add(serviceType.cast(plugin));
		}

		return plugins;
	}

}
