package com.z.monit.bootstrap.dubbo.plugin;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import com.z.monit.bootstrap.core.instrument.InstrumentException;
import com.z.monit.bootstrap.core.instrument.MonitPlugin;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;
import com.z.monit.bootstrap.core.instrument.TransformCallback;
import com.z.monit.bootstrap.core.intercept.DefaultInterceptor;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.core.intercept.register.InterceptorRegister;
import com.z.monit.bootstrap.core.util.InstrumentCodeUtil;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class DubboPlugin implements MonitPlugin {
	
	private static CtClass addBefore(String className, String method, String codeContent) {
		System.out.println(">addBefore");
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
		
		CtClass ctClass = null;
		try {
			ctClass = classPool.get(className);

			CtMethod oldMethod = ctClass.getDeclaredMethod(method);

			// 拷贝旧方法
			CtMethod newMehtod = CtNewMethod.copy(oldMethod, ctClass, null);

			// 原方法改名为＝原方法名$$Old
			String oldMethodName = method + "$$Old";
			oldMethod.setName(oldMethodName);

			// 新方法需要旧方法逻辑前加上codeContent代码
			StringBuilder sb = new StringBuilder(100);
			sb.append("{\n");
			sb.append(codeContent);
			sb.append(oldMethodName + "($$);\n");
			sb.append("}");
			newMehtod.setBody(sb.toString());
			newMehtod.setName(method);
			ctClass.addMethod(newMehtod);

			System.out.println("==codeRes[" + sb + "]");

			return ctClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Map<String, TransformCallback> addTransformCallBack() {
		Map<String, TransformCallback> transformCallBackMap = new HashMap<String, TransformCallback>();

		String className="test.monit.test.A";
		
		/**
		 * 注册服务提供方织入逻辑
		 */
		TransformCallback providerTransformCallBack = new TransformCallback() {
			public byte[] doInTransform(PluginInstrumentService instrumentService, ClassLoader loader, String className,
					Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
							throws InstrumentException {
				System.out.println("17,7:49,do provider callback");
				
				String codeContent = "System.out.println(\"====访问主页\");";
				
				CtClass ctClass=InstrumentCodeUtil.addBefore(className, "show", codeContent);
				
				InterceptorRegister.register("a", new DefaultInterceptor());
				Interceptor res=InterceptorRegister.getInterceptor("a");
				System.out.println(res);

				try {
					return ctClass.toBytecode();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		};
		
		transformCallBackMap.put(className, providerTransformCallBack);
		
//		transformCallBackMap.put("com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker", providerTransformCallBack);

		/**
		 * 注册服务消费方织入逻辑<br/>
		 * TODO
		 */

		return transformCallBackMap;
	}

}
