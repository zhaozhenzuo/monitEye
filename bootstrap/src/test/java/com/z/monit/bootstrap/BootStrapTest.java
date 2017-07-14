//package com.z.monit.bootstrap;
//
//import java.io.File;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLClassLoader;
//
//import org.junit.Test;
//
//import com.z.monit.bootstrap.core.instrument.ClassFileTransformDispatcher;
//import com.z.monit.test.base.InstrumentationForTest;
//
//public class BootStrapTest {
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testBootStrap() throws Exception {
//		System.out.println("testBootstrap");
//
//		String javaClassPath = "java.class.path";
//		String javaClassValue = System.getProperty(javaClassPath);
//		System.setProperty("java.class.path",
//				javaClassValue + ":" + "/Users/zhaozhenzuo/Documents/monit/bootstrap-1.0.jar");
//
//		BootStrap.premain("test", new InstrumentationForTest());
//
//		/**
//		 * 模拟webAppClassLoader启动
//		 */
//		File file = new File("/Users/zhaozhenzuo/Documents/monit/web/dubbo-2.5.3.jar");
//		URL url = file.toURI().toURL();
//		URLClassLoader webAppClassLoader = new URLClassLoader(new URL[] { url });
//
//		ClassFileTransformDispatcher classFileTransformDispatcher = BootStrapStart.agent
//				.getClassFileTransformDispatcherForTest();
//
//		classFileTransformDispatcher.transform(webAppClassLoader, "com.z.monit.appClass.A",
//				null, null, null);
//
//		@SuppressWarnings("rawtypes")
//		// Class invokerClass =
//		// webAppClassLoader.loadClass("com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker");
//		// System.out.println(invokerClass);
//
//		Class invokerClass = webAppClassLoader.loadClass("com.z.monit.appClass.A");
//		System.out.println(invokerClass);
//
//		Object a = invokerClass.newInstance();
//
//		Method method = invokerClass.getMethod("show", null);
//
//		method.invoke(a, null);
//
//	}
//
//}
