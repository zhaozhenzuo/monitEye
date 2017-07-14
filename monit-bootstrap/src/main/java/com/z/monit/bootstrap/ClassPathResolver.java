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

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bootstrap jar,bootstrap-core jar路径解析
 * 
 * @author zhaozhenzuo
 *
 */
public class ClassPathResolver {

	private final Logger logger = LoggerFactory.getLogger(ClassPathResolver.class);

	private static final Pattern DEFAULT_BOOTSTRAP_PATTERN = Pattern
			.compile("monit-bootstrap(-[0-9]+\\.[0-9]+(\\-SNAPSHOT)?)?\\.jar");

	private static final Pattern DEFAULT_BOOTSTRAP_CORE_PATTERN = Pattern
			.compile("monit-core(-[0-9]+\\.[0-9]+(\\-SNAPSHOT)?)?\\.jar");

	private static final Pattern PLUGIN_PATTERN = Pattern
			.compile("[a-z]*-plugin(-[0-9]+\\.[0-9]+(\\-SNAPSHOT)?)?\\.jar");

	/**
	 * java.class.path,类目录,java agent启动时agent jar也会在这个目录里
	 */
	private String classPath;

	/**
	 * 启动boostrap jar目录
	 */
	private String bootstrapDir;

	/**
	 * bootstrap及bootstrap-core jar包所在目录,
	 */
	private String bootstrapCoreDir;

	/**
	 * default lib dir
	 */
	private String defaultLibDir;

	/**
	 * plugin目录
	 */
	private String pluginDir;

	/**
	 * 启动jar url
	 */
	private String bootstrapFullUrl;

	/**
	 * monit核心jar包url
	 */
	private String bootstrapCoreFullUrl;

	/**
	 * monit lib包下urls
	 */
	private URL[] libUrls;

	/**
	 * 插件url集合
	 */
	private URL[] pluginUrls;

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public ClassPathResolver() {
		this(getClassPathFromSystemProperty());
	}

	public ClassPathResolver(String classPath) {
		this.classPath = classPath;
	}

	public List<String> getDefaultFileExtensionList() {
		List<String> extensionList = new ArrayList<String>();
		extensionList.add("jar");
		extensionList.add("xml");
		extensionList.add("properties");
		return extensionList;
	}

	public void setClassPathFromSystemProperty() {
		this.classPath = getClassPathFromSystemProperty();
	}

	public static String getClassPathFromSystemProperty() {
		return System.getProperty("java.class.path");
	}

	public boolean initBootStrapJar() {
		Matcher matcher = DEFAULT_BOOTSTRAP_PATTERN.matcher(classPath);
		if (!matcher.find()) {
			return false;
		}
		String bootstrapJarName = parseBootstrapName(matcher);
		String bootstrapFullPath = parseBootstrapPath(classPath, bootstrapJarName);
		if (bootstrapFullPath == null) {
			return false;
		}
		String bootstrapDirPath = parseBootstrapDirPath(bootstrapFullPath);

		/**
		 * 将bootstrap相关url全部放入MonitConfigRegistry
		 */
		this.bootstrapDir = bootstrapDirPath;
		this.bootstrapFullUrl = bootstrapFullPath;
		return true;
	}

	public boolean initBootStrapCore() {
		this.bootstrapCoreFullUrl = parseBootstrapCoreFullUrl();
		return true;
	}

	public boolean initPlugin() {
		URL[] plugins = this.parsePluginUrls();
		this.pluginUrls = plugins;
		if (plugins == null || plugins.length <= 0) {
			return false;
		}
		return true;
	}

	public boolean initLibUrls() {
		URL[] defaultLibUrls = this.parseLibUrls();
		this.libUrls = defaultLibUrls;
		if (defaultLibUrls == null || defaultLibUrls.length <= 0) {
			return false;
		}
		return true;
	}

	private String parseBootstrapCoreFullUrl() {
		if (bootstrapDir == null) {
			logger.error(">>boostrap dir not parse");
			return null;
		}

		bootstrapCoreDir = bootstrapDir;

		File coreDirFile = new File(bootstrapCoreDir);
		if (!coreDirFile.isDirectory()) {
			logger.error(">>boostrap dir is not dir,bootstraopDir:" + bootstrapDir);
			return null;
		}

		File[] files = coreDirFile.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Matcher matcher = DEFAULT_BOOTSTRAP_CORE_PATTERN.matcher(name);
				if (matcher.matches()) {
					return true;
				}
				return false;
			}
		});

		if (files == null) {
			logger.error(">>not found bootstrap core jar");
		} else if (files.length != 1) {
			logger.error(">>too many bootstrap core jars found");
		}
		return files[0].getAbsolutePath();
	}

	private URL[] parsePluginUrls() {
		if (bootstrapDir == null) {
			logger.error(">>boostrap dir not parse");
			return null;
		}

		pluginDir = bootstrapDir + "/" + "plugin";
		File pluginDirFile = new File(pluginDir);
		if (!pluginDirFile.isDirectory()) {
			logger.error(">>plugin dir is not dir,pluginDir:" + pluginDir);
			return null;
		}

		File[] files = pluginDirFile.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Matcher matcher = PLUGIN_PATTERN.matcher(name);
				if (matcher.matches()) {
					return true;
				}
				return false;
			}
		});

		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			urls[i] = toURI(files[i]);
		}
		return urls;
	}

	private URL[] parseLibUrls() {
		if (bootstrapDir == null) {
			logger.error(">>boostrap dir not parse");
			return null;
		}

		defaultLibDir = bootstrapDir + "/" + "libs";
		File defaultLibDirFile = new File(defaultLibDir);
		if (!defaultLibDirFile.isDirectory()) {
			logger.error(">>defaultLibDir is not dir,defaultLibDir:" + defaultLibDir);
			return null;
		}

		File[] files = defaultLibDirFile.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return true;
			}
		});

		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			urls[i] = toURI(files[i]);
		}
		return urls;
	}

	private URL toURI(File file) {
		URI uri = file.toURI();
		try {
			return uri.toURL();
		} catch (MalformedURLException e) {
			logger.error(".toURL() failed. Error:" + e.getMessage(), e);
			return null;
		}
	}

	private String parseBootstrapName(Matcher matcher) {
		int start = matcher.start();
		int end = matcher.end();
		return this.classPath.substring(start, end);
	}

	private String parseBootstrapPath(String classPath, String bootstrapJar) {
		String[] classPathList = classPath.split(File.pathSeparator);
		for (String findPath : classPathList) {
			boolean find = findPath.contains(bootstrapJar);
			if (find) {
				return findPath;
			}
		}
		return null;
	}

	public String parseBootstrapDirPath(String bootstrapJarFullPath) {
		int index1 = bootstrapJarFullPath.lastIndexOf("/");
		int index2 = bootstrapJarFullPath.lastIndexOf("\\");
		int max = Math.max(index1, index2);
		if (max == -1) {
			return null;
		}
		return bootstrapJarFullPath.substring(0, max);
	}

	public String getBootstrapDir() {
		return bootstrapDir;
	}

	public void setBootstrapDir(String bootstrapDir) {
		this.bootstrapDir = bootstrapDir;
	}

	public String getBootstrapCoreDir() {
		return bootstrapCoreDir;
	}

	public void setBootstrapCoreDir(String bootstrapCoreDir) {
		this.bootstrapCoreDir = bootstrapCoreDir;
	}

	public String getDefaultLibDir() {
		return defaultLibDir;
	}

	public void setDefaultLibDir(String defaultLibDir) {
		this.defaultLibDir = defaultLibDir;
	}

	public String getPluginDir() {
		return pluginDir;
	}

	public void setPluginDir(String pluginDir) {
		this.pluginDir = pluginDir;
	}

	public String getBootstrapFullUrl() {
		return bootstrapFullUrl;
	}

	public void setBootstrapFullUrl(String bootstrapFullUrl) {
		this.bootstrapFullUrl = bootstrapFullUrl;
	}

	public String getBootstrapCoreFullUrl() {
		return bootstrapCoreFullUrl;
	}

	public void setBootstrapCoreFullUrl(String bootstrapCoreFullUrl) {
		this.bootstrapCoreFullUrl = bootstrapCoreFullUrl;
	}

	public URL[] getLibUrls() {
		return libUrls;
	}

	public void setLibUrls(URL[] libUrls) {
		this.libUrls = libUrls;
	}

	public URL[] getPluginUrls() {
		return pluginUrls;
	}

	public void setPluginUrls(URL[] pluginUrls) {
		this.pluginUrls = pluginUrls;
	}


}
