package com.z.monit.agent.instrument;

import javassist.NotFoundException;

public interface ClassPoolService<T> {

	public T getCtClass(ClassLoader classLoader, String className, byte[] classfileBuffer) throws NotFoundException;

}
