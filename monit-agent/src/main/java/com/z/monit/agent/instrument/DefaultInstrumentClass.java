package com.z.monit.agent.instrument;

import com.z.monit.agent.util.JavaAssistUtils;
import com.z.monit.bootstrap.core.context.AgentContext;
import com.z.monit.bootstrap.core.instrument.InstrumentClass;
import com.z.monit.bootstrap.core.instrument.InstrumentMethod;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;

public class DefaultInstrumentClass implements InstrumentClass {

	private AgentContext agentContext;

	private CtClass targetCtClass;
	
	public DefaultInstrumentClass(CtClass targetCtClass, AgentContext agentContext) {
		this.targetCtClass = targetCtClass;
		this.agentContext = agentContext;
	}

	public void addMethod(String methodName, String... args) {
		throw new UnsupportedOperationException("unsupported addMethod");
	}

	public InstrumentMethod getDeclairedMethod(String methodName, String... args) {
		final String jvmSignature = JavaAssistUtils.javaTypeToJvmSignature(args);

		InstrumentMethod instrumentMethod=new DefaultInstrumentMethodImpl();
		
		for (CtMethod method : targetCtClass.getDeclaredMethods()) {
			if (!method.getName().equals(methodName)) {
				continue;
			}

			final String descriptor = method.getMethodInfo2().getDescriptor();
			if (descriptor.startsWith(jvmSignature)) {
			}
		}

		return instrumentMethod;
	}

}
