package com.z.monit.agent.instrument;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.z.monit.bootstrap.core.intercept.Interceptor;

public class DefaultInterceptorRegisterImpl implements InterceptorRegister {

	private Map<Integer, Interceptor> registerMap = new ConcurrentHashMap<Integer, Interceptor>();

	private AtomicInteger atomicId = new AtomicInteger(0);

	public int registerInterceptorAndGetId(Interceptor interceptor) {
		int nextId = atomicId.incrementAndGet();
		registerMap.put(nextId, interceptor);
		return nextId;
	}

	public Interceptor getInterceptorById(int id) {
		return registerMap.get(id);
	}

}
