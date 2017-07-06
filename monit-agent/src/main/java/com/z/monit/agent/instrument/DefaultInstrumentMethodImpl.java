package com.z.monit.agent.instrument;

import com.z.monit.bootstrap.core.instrument.InstrumentMethod;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import javassist.CtBehavior;
import javassist.CtClass;

public class DefaultInstrumentMethodImpl implements InstrumentMethod {

	// 要织入的类
	private CtClass ctClass;

	// 要织入的方法,构造器等
	private CtBehavior ctBehavior;
	

	public void addInterceptor(String interceptorName) {
		
		

	}

	public static void main(String[] args) {

	}

}
