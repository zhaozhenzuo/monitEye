package com.z.monit.bootstrap.dubbo.plugin;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import com.z.monit.bootstrap.core.instrument.InstrumentException;
import com.z.monit.bootstrap.core.instrument.MonitPlugin;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;
import com.z.monit.bootstrap.core.instrument.TransformCallback;

import javassist.CannotCompileException;
import javassist.CtClass;

public class DubboPlugin implements MonitPlugin {

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
