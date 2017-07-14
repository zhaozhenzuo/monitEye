package org.dubbo.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.z.monit.bootstrap.core.agent.DefaultAgentParam;
import com.z.monit.bootstrap.core.constants.MonitConstants;
import com.z.monit.bootstrap.dubbo.interceptor.DubboProvideInterceptor;
import com.z.monit.codegen.javassit.InstrumentCodeUtil;
import com.z.monit.codegen.javassit.InterceptorGenerateCodeUtil;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JavassitTest {

	public static void test() throws NotFoundException, CannotCompileException, IOException {
		final ClassPool pool = ClassPool.getDefault();
		pool.appendClassPath(new ClassClassPath(JavassitTest.class));

		final CtClass compiledClass = pool.get("com.z.monit.bootstrap.dubbo.plugin.TestClass");
		final CtMethod method = compiledClass.getDeclaredMethod("show");

		method.addLocalVariable("startMs", CtClass.longType);

		String beforeCode = "System.out.println(\"before\");";

		method.insertBefore(beforeCode + "\n");

		String afterCode = "System.out.println(\"after\");";

		method.insertAfter(afterCode);
		
		CtClass etype = ClassPool.getDefault().get("java.io.IOException");
		method.addCatch("{ System.out.println($e); throw $e; }", etype);

		compiledClass.writeFile("/Users/zhaozhenzuo/Documents/temp/");

	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException,
			CannotCompileException, IOException, NotFoundException {

		test();

//		 Map<String, Object> configMap = new HashMap<String, Object>();
//		 DefaultAgentParam.configMap = configMap;
//		
//		 DefaultAgentParam.configMap.put(MonitConstants.bootstrapCoreDir,
//		 "/Users/zhaozhenzuo/Documents/monit");
//		 DefaultAgentParam.configMap.put(MonitConstants.pluginDir,
//		 "/Users/zhaozhenzuo/Documents/monit/plugin");
//		 DefaultAgentParam.configMap.put(MonitConstants.defaultLibDir,
//		 "/Users/zhaozhenzuo/Documents/monit/libs");
//		
//		 final String providerClassName =
//		 "com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker";
//		 CtClass ctClass =
//		 InstrumentCodeUtil.addBeforeAndAfterInterceptor(providerClassName,
//		 new DubboProvideInterceptor(), "invoke",
//		 com.alibaba.dubbo.rpc.Invocation.class);
//		
//		 ctClass.writeFile("/Users/zhaozhenzuo/Documents/temp/");

	}

}
