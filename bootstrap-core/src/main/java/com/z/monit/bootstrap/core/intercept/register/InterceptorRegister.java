package com.z.monit.bootstrap.core.intercept.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.z.monit.bootstrap.core.intercept.Interceptor;

public class InterceptorRegister {

	private static final Map<String/* key */, Interceptor> regiterMap = new ConcurrentHashMap<String, Interceptor>();

	public static void register(String key, Interceptor interceptor) {
		regiterMap.put(key, interceptor);
	}

	public static Interceptor getInterceptor(String key) {
		return regiterMap.get(key);
	}

}
