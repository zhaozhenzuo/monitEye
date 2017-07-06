package com.z.monit.bootstrap.core.instrument;

import java.security.ProtectionDomain;

/**
 * 业务方写plugin时,提供的织入逻辑<br/>
 * 如dubbo monit，需要对AbstractProxyInvoker织入一段逻辑,因此这个回调接口用于给plugin实现方去实现
 * 
 * @author zhaozhenzuo
 *
 */
public interface TransformCallback {

	public byte[] doInTransform(PluginInstrumentService instrumentService, ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
					throws InstrumentException;

}
