package org.dubbo.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.z.monit.bootstrap.core.agent.DefaultAgentParam;
import com.z.monit.bootstrap.core.constants.MonitConstants;
import com.z.monit.bootstrap.dubbo.interceptor.DubboProvideInterceptor;
import com.z.monit.codegen.javassit.InstrumentCodeUtil;

import javassist.CannotCompileException;
import javassist.CtClass;

public class JavassitTest {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, CannotCompileException, IOException {
		
		
		Map<String, Object> configMap=new HashMap<String, Object>();
		DefaultAgentParam.configMap=configMap;
		
		DefaultAgentParam.configMap.put(MonitConstants.bootstrapCoreDir, "/Users/zhaozhenzuo/Documents/monit");
		DefaultAgentParam.configMap.put(MonitConstants.pluginDir, "/Users/zhaozhenzuo/Documents/monit/plugin");
		DefaultAgentParam.configMap.put(MonitConstants.defaultLibDir, "/Users/zhaozhenzuo/Documents/monit/libs");
		
		final String providerClassName="com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker";
		CtClass ctClass = InstrumentCodeUtil.addBeforeInterceptor(
				providerClassName, new DubboProvideInterceptor(), "invoke",
				com.alibaba.dubbo.rpc.Invocation.class);
		
		ctClass.writeFile("/Users/zhaozhenzuo/Documents/temp/");

//		Class claz = ctClass.toClass();

//		A a = (A) claz.newInstance();
//
//		a.show(object, objArgs);

	}

}
