package com.z.monit.agent.instrument;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.z.monit.bootstrap.core.instrument.TransformCallback;
import com.z.monit.bootstrap.core.instrument.TransformCallbackRegister;

public class DefaultTransformCallbackRegister implements TransformCallbackRegister {

	private Map<String, TransformCallback> transformCallbackRegisterMap = new ConcurrentHashMap<String, TransformCallback>();

	public void registerTransform(String classNameInjected, TransformCallback transformCallBack) {
		transformCallbackRegisterMap.put(classNameInjected, transformCallBack);
	}

	public TransformCallback geTransformCallbackByClassNameInjected(String classNameInjected) {
		return transformCallbackRegisterMap.get(classNameInjected);
	}

}
