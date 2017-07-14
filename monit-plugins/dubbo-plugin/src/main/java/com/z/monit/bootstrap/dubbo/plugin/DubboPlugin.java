package com.z.monit.bootstrap.dubbo.plugin;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import com.z.monit.bootstrap.core.instrument.InstrumentException;
import com.z.monit.bootstrap.core.instrument.MonitPlugin;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;
import com.z.monit.bootstrap.core.instrument.TransformCallback;
import com.z.monit.bootstrap.dubbo.interceptor.DubboConsumerInterceptor;
import com.z.monit.bootstrap.dubbo.interceptor.DubboProvideInterceptor;
import com.z.monit.codegen.javassit.InstrumentCodeUtil;
import javassist.CannotCompileException;
import javassist.CtClass;

public class DubboPlugin implements MonitPlugin {

	public Map<String, TransformCallback> addTransformCallBack() {
		Map<String, TransformCallback> transformCallBackMap = new HashMap<String, TransformCallback>();

		final String providerClassName = "com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker";
		final String consumerClassName = "com.alibaba.dubbo.rpc.protocol.dubbo.filter.FutureFilter";

		/**
		 * 注册服务提供方织入逻辑
		 */
		TransformCallback providerTransformCallBack = new TransformCallback() {
			public byte[] doInTransform(PluginInstrumentService instrumentService, ClassLoader loader, String className,
					Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
							throws InstrumentException {

				CtClass ctClass = InstrumentCodeUtil.addBeforeAndAfterInterceptor(providerClassName,
						new DubboProvideInterceptor(), "invoke", com.alibaba.dubbo.rpc.Invocation.class);

				try {
					return InstrumentCodeUtil.ctClassToByteArr(ctClass);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}

				return null;
			}
		};

		TransformCallback consumerTransformCallBack = new TransformCallback() {
			public byte[] doInTransform(PluginInstrumentService instrumentService, ClassLoader loader, String className,
					Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
							throws InstrumentException {

				CtClass ctClass = InstrumentCodeUtil.addBeforeAndAfterInterceptor(consumerClassName,
						new DubboConsumerInterceptor(), "invoke", com.alibaba.dubbo.rpc.Invoker.class,
						com.alibaba.dubbo.rpc.Invocation.class);

				try {
					return ctClass.toBytecode();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}

				return null;
			}
		};

		transformCallBackMap.put(providerClassName, providerTransformCallBack);
		transformCallBackMap.put(consumerClassName, consumerTransformCallBack);
		return transformCallBackMap;
	}

}
