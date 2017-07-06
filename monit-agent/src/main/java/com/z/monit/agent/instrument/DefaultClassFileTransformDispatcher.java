package com.z.monit.agent.instrument;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.monit.bootstrap.core.instrument.ClassFileTransformDispatcher;
import com.z.monit.bootstrap.core.instrument.InstrumentException;
import com.z.monit.bootstrap.core.instrument.PluginInstrumentService;
import com.z.monit.bootstrap.core.instrument.TransformCallback;
import com.z.monit.bootstrap.core.instrument.TransformCallbackRegister;

public class DefaultClassFileTransformDispatcher implements ClassFileTransformDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(DefaultClassFileTransformDispatcher.class);

	private TransformCallbackRegister transformCallbackRegister;

	private PluginInstrumentService instrumentService;

	public DefaultClassFileTransformDispatcher(TransformCallbackRegister transformCallbackRegister,
			PluginInstrumentService instrumentService) {
		this.transformCallbackRegister = transformCallbackRegister;
		this.instrumentService = instrumentService;
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		logger.info(">>dispatch className:" + className);
		return dispatchTransform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
	}

	/**
	 * 根据类名找到对应织入回调处理器,并调用它产生织入后的字节码并返回
	 * 
	 * @param loader
	 * @param className
	 * @param classBeingRedefined
	 * @param protectionDomain
	 * @param classfileBuffer
	 * @return
	 */
	private byte[] dispatchTransform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) {
		
		String classNameTransfer=className.replace("/", ".");
		
		TransformCallback transformCallback = transformCallbackRegister
				.geTransformCallbackByClassNameInjected(classNameTransfer);
		
		if(transformCallback == null){
			return null;
		}
		
		byte[] res = null;
		try {
			res = transformCallback.doInTransform(instrumentService, loader, classNameTransfer, classBeingRedefined,
					protectionDomain, classfileBuffer);
		} catch (InstrumentException e) {
			logger.error(">>doInTransform err,className:" + className, e);
		}
		return res;
	}

}
